package com.example.loan.service;

import com.example.loan.dao.UserAccountRepository;
import com.example.loan.dao.UserInformationRepository;
import com.example.loan.dao.entity.UserAccount;
import com.example.loan.dao.entity.UserInformation;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.RandomNumberGenerator;
import com.example.loan.utils.ResponseResult;
import com.example.loan.utils.SendCodeUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserInformationRepository userInformationRepository;

    public ResponseResult<String> sendEmailCode(String email){
        if(!userInformationRepository.existsUserInformationByEmail(email)){
            return  ResponseResult.error("error",404,"该邮箱还未注册");
        }

        String code= RandomNumberGenerator.generateValidateCode();

        if(!email.substring(email.length()-6,email.length()-4).equals("qq")){
            return ResponseResult.error("请使用qq邮箱",400,null);
        }

        SendCodeUtils.sendEmailCode(javaMailSender,email,"您的“放心用”验证码",code);
        redisService.set(email+"code",code,10, TimeUnit.MINUTES);
        return ResponseResult.success(200,"success",code);
    }

    public String judgeEmailCode(String code,String email){
        String code1=(String) redisService.get(email+"code");
        if(code==null){
            return "验证码不能为空";
        }
        if(!code.equals(code1)){
            return "验证码错误或验证码已过期";
        }
        UserInformation userInformation= userInformationRepository.findUserInformationByEmail(email);
        UserAccount userAccount= userAccountRepository.findUserAccountById(userInformation.getId());
        String key=userAccount.getName()+":token";
        String token= JwtUtils.createToken(userAccount.getPersonality(),userAccount.getName());
        redisService.set(key,token,24, TimeUnit.HOURS);
        return token;
    }
}
