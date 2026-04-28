package com.example.loan.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.example.loan.dao.entity.CaptchaV0;
import com.example.loan.service.RedisService;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/Captcha")
public class CaptchaController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/graph-captcha")
    public ResponseResult<CaptchaV0> getCaptcha(String captchaId){
        CircleCaptcha circleCaptcha= CaptchaUtil.createCircleCaptcha(130,48,4,10);
        String captchaText=circleCaptcha.getCode();
        String captchaImageBase64Data=circleCaptcha.getImageBase64Data();
        captchaId= Optional.ofNullable(captchaId).orElseGet(()-> UUID.randomUUID().toString());
        redisService.set("captcha:"+captchaId,captchaText,30, TimeUnit.SECONDS);
        CaptchaV0 data=CaptchaV0.builder()
                .captchaId(captchaId)
                .captchaImage(captchaImageBase64Data)
                .build();
        return ResponseResult.success(200,"success",data);
    }
}
