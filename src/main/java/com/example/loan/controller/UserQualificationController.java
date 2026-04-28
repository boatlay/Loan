package com.example.loan.controller;

import com.example.loan.dao.entity.UserAccount;
import com.example.loan.dao.entity.UserQualification;
import com.example.loan.service.UserAccountService;
import com.example.loan.service.UserQualificationService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan/qualification")
public class UserQualificationController {

    @Autowired
    UserQualificationService userQualificationService;

    @Autowired
    UserAccountService userAccountService;

    @PostMapping
    public ResponseResult<Integer> addUserQualification(@RequestBody UserQualification userQualification,@RequestHeader String token){
//        HttpSession session=request.getSession();
//        String name=(String) session.getAttribute("name");
        String name= JwtUtils.getNameFromJwt(token);
        UserAccount userAccount=userAccountService.getUserAccountByName(name);
        userQualificationService.addUserQualification(userQualification,userAccount.getId());
        return ResponseResult.success(200,"success",userAccount.getId());
    }
}
