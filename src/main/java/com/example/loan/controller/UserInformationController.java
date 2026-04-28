package com.example.loan.controller;

import com.example.loan.dao.entity.UserAccount;
import com.example.loan.dao.entity.UserInformation;
import com.example.loan.service.UserAccountService;
import com.example.loan.service.UserInformationService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/information")
    public ResponseResult<Void> addInformation(@RequestBody UserInformation userInformation, @RequestHeader String token){
        //...
        String name= JwtUtils.getNameFromJwt(token);
        UserAccount userAccount=userAccountService.getUserAccountByName(name);
        userInformationService.addInformation(userInformation,userAccount.getId());
        return ResponseResult.success();
    }

    @PutMapping("/information")
    public ResponseResult<Void> updateInformation(@RequestBody UserInformation userInformation,@RequestHeader String token){
//        HttpSession session=request.getSession();
//        String name=(String) session.getAttribute("name");
        String name= JwtUtils.getNameFromJwt(token);
        UserAccount userAccount=userAccountService.getUserAccountByName(name);
        if(userInformationService.updateInformation(userInformation,userAccount.getId())==1){
            return ResponseResult.success();
        }
        return ResponseResult.error("你还没有完善过个人资料",404,null);
    }
}
