package com.example.loan.controller;

import com.example.loan.dao.entity.AuditAccount;
import com.example.loan.exception.UserAccountNotFoundException;
import com.example.loan.service.AuditAccountService;
import com.example.loan.service.RedisService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/adamin")
public class AuditAccountController {

    @Autowired
    private AuditAccountService auditAccountService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody AuditAccount auditAccount){
        if(auditAccountService.login(auditAccount)==0){
            throw new UserAccountNotFoundException();
        }else {
//            HttpSession session=request.getSession();
//            session.setAttribute("name",auditAccount.getName());
            String token= JwtUtils.createToken(auditAccount.getPersonality(),auditAccount.getName());
            redisService.set(auditAccount.getName()+":token",token,24, TimeUnit.HOURS);
            return ResponseResult.success(200,"success",token);
        }
    }

    @PutMapping("/modify")
    public ResponseResult<String> modify(@RequestBody AuditAccount auditAccount,@RequestHeader String token){
//        HttpSession session=request.getSession();
//        String name=(String) session.getAttribute("name");
        String name=JwtUtils.getNameFromJwt(token);
        AuditAccount auditAccount1=auditAccountService.modify(auditAccount,name);

//        session.setAttribute("name",auditAccount1.getName());
        redisService.delete(name+":token");
        String token1=JwtUtils.createToken(auditAccount1.getPersonality(),auditAccount1.getName());
        redisService.set(auditAccount1.getName()+":token",token1,24,TimeUnit.HOURS);
        return ResponseResult.success(200,"success",token1);
    }

    @PostMapping("/register")
    public ResponseResult<Void> register(@RequestBody AuditAccount auditAccount){
        auditAccountService.register(auditAccount);
        return ResponseResult.success();
    }
}
