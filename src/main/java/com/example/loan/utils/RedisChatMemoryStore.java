package com.example.loan.utils;

import com.example.loan.service.RedisService;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private RedisService redisService;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String json=(String) redisService.get(memoryId.toString());
        List<ChatMessage> list=ChatMessageDeserializer.messagesFromJson(json);
        return list;
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        String json= ChatMessageSerializer.messagesToJson(list);
        redisService.set(memoryId.toString(),json,24, TimeUnit.HOURS);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        redisService.delete(memoryId.toString());
        return;
    }
}
