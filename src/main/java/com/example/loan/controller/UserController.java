package com.example.loan.controller;

import com.example.loan.service.RedisService;
import com.example.loan.service.UserService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @GetMapping("/mail/code")
    public ResponseResult<String> sendCodeByEmail(@RequestParam String email){
        return userService.sendEmailCode(email);
    }

    @GetMapping("/mail/judge")
    public ResponseResult<Map<String,String>> judgeEmailCode(@RequestParam String code, @RequestParam String email){
        String result=userService.judgeEmailCode(code,email);
        if(result.substring(0,3).equals("验证码")){
            return ResponseResult.error(result,400,null);
        }else {
            Map map=new HashMap();
            map.put("name",JwtUtils.getNameFromJwt(result));
            map.put("token",result);
            return ResponseResult.success(200,"success",map);
        }
    }
}