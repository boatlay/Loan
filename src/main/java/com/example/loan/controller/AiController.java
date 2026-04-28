package com.example.loan.controller;

import com.example.loan.service.UserPermissionService;
import com.example.loan.service.aiService.AppConsultantService;
import com.example.loan.service.aiService.ConsultantService;
import com.example.loan.service.aiService.CrawlerService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiController {
    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private AppConsultantService appConsultantService;

    @Autowired
    private UserPermissionService userPermissionService;

    @GetMapping(value = "/chat",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestParam String memoryId, @RequestParam String message){
        Flux<String> result=consultantService.streamingChat(memoryId,message);
        return result;
    }

    @GetMapping("/chat_for_app")
    public String chatForApp(@RequestParam String memoryId,@RequestParam String message){
        String result= appConsultantService.chat(memoryId,message);
        return result;
    }

    @PostMapping("/crawler")
    public ResponseResult<Void> crawl(@RequestHeader String token){
        String message="调用你的爬虫插件,帮我爬虫:https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E8%80%81%E8%B5%96";
        String name= JwtUtils.getNameFromJwt(token);
        userPermissionService.crawl(name,message);
        return ResponseResult.success();
    }
}