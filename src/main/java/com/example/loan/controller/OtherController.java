package com.example.loan.controller;

import com.example.loan.dao.entity.LoanApply;
import com.example.loan.dao.entity.RepayPlan;
import com.example.loan.exception.LoanApplyNotFoundException;
import com.example.loan.exception.RepayPlanNotFoundException;
import com.example.loan.service.LoanApplyService;
import com.example.loan.service.RepayPlanService;
import com.example.loan.service.UserInformationService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/other")
public class OtherController {
    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private LoanApplyService loanApplyService;

    @Autowired
    private RepayPlanService repayPlanService;

    @GetMapping("/score")
    public ResponseResult<Map<String,Object>> getScore(@RequestHeader String token){
//        HttpSession session=request.getSession();
//        String name=(String)session.getAttribute("name");
        String name= JwtUtils.getNameFromJwt(token);
        int score=userInformationService.getScore(name);
        Map<String,Object>map=new HashMap<>();
        map.put("name",name);
        map.put("creditScore",score);
        return ResponseResult.success(200,"success",map);
    }

    @GetMapping("/status")
    public ResponseResult<List<Map<String, Object>>> getStatus(@RequestHeader String token){
        String name= JwtUtils.getNameFromJwt(token);

        List<LoanApply> list= loanApplyService.getApplyByName(name);
        if(list.size() != 0){
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (LoanApply loanApply : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("applyStatus", loanApply.getApplyStatus());
                dataList.add(map);
            }
            return ResponseResult.success(200, "success", dataList);
        }else{
            throw new LoanApplyNotFoundException();
        }
    }
}
