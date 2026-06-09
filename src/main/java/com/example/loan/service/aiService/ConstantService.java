package com.example.loan.service.aiService;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        tools = "reportTool")
public interface ConstantService {

    @SystemMessage(fromResource = "constant.txt")
    public String chat(@UserMessage String message);
}