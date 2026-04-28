package com.example.loan.config;

import com.alibaba.dashscope.app.Application;
import com.alibaba.dashscope.app.ApplicationParam;
import com.alibaba.dashscope.app.ApplicationResult;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@Configuration
@EnableAsync
public class CommonConfig {

    @Autowired
    private OpenAiChatModel model;

    @Autowired
    private ChatMemoryStore redisChatMemoryStore;

    @Value("${dashscope.crawler.app.id}")
    private String bailianAppId;

    @Value("${dashscope.api.key}")
    private String apiKey;

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        ChatMemoryProvider chatMemoryProvider=new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(20)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();
            }
        };
        return chatMemoryProvider;
    }

    @Bean
    public EmbeddingStore store(){
        List<Document> documents= ClassPathDocumentLoader.loadDocuments("content");
        InMemoryEmbeddingStore store=new InMemoryEmbeddingStore();
        EmbeddingStoreIngestor ingestor=EmbeddingStoreIngestor.builder()
                .embeddingStore(store)
                .build();
        ingestor.ingest(documents);
        return store;
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore store){
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .minScore(0.5)
                .maxResults(3)
                .build();
    }

    @Bean
    public ChatModel bailianAppChatModel(){
        return new ChatModel() {
            private final Application application=new Application();
            @Override
            public ChatResponse doChat(ChatRequest chatRequest) {
                ChatMessage lastMessage=chatRequest.messages().get(chatRequest.messages().size()-1);
                String lastUserMessage="";
                if(lastMessage instanceof dev.langchain4j.data.message.UserMessage){
                    lastUserMessage=((dev.langchain4j.data.message.UserMessage)lastMessage).singleText();
                }else {
                    lastUserMessage=lastMessage.toString();
                }

                try {
                    ApplicationParam param=ApplicationParam.builder()
                            .apiKey(apiKey)
                            .appId(bailianAppId)
                            .prompt(lastUserMessage)
                            .build();

                    ApplicationResult result=application.call(param);
                    String answer=result.getOutput().getText();

                    return ChatResponse.builder()
                            .aiMessage(AiMessage.from(answer))
                            .build();
                }catch (Exception e){
                    throw new RuntimeException("调用百炼云端智能体失败: " + e.getMessage(), e);
                }
            }
        };
    }
}
