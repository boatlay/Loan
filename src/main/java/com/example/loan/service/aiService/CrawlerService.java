package com.example.loan.service.aiService;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import org.springframework.stereotype.Service;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "bailianAppChatModel"
)
public interface CrawlerService {

    public String chat(@UserMessage String message);
}
