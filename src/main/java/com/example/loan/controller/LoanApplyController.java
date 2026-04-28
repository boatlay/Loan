package com.example.loan.controller;

import com.example.loan.dao.entity.LoanApply;
import com.example.loan.exception.ApplyIllegalException;
import com.example.loan.exception.LoanApplyNotFoundException;
import com.example.loan.service.LoanApplyService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanApplyController {
    @Autowired
    private LoanApplyService loanApplyService;

    @PostMapping("/apply")
    public ResponseResult<Integer> complete(@RequestBody LoanApply loanApply){
        if(loanApplyService.complete(loanApply)){
            return ResponseResult.success(200, "success", loanApply.getId());
        }else{
            throw new ApplyIllegalException();
        }
    }

    @GetMapping("/history")
    public ResponseResult<List<LoanApply>> get(@RequestHeader String token) throws UnsupportedEncodingException {
//        HttpSession session = httpServletRequest.getSession();
//        String name = (String) session.getAttribute("name");

        String name= JwtUtils.getNameFromJwt(token);

        List<LoanApply> list = loanApplyService.getApplyByName(name);
        if(list.size() != 0){
            return ResponseResult.success(200, "success", list);
        }else{
            throw new LoanApplyNotFoundException();
        }
    }
}
