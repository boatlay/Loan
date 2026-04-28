package com.example.loan.controller;

import com.example.loan.dao.entity.RepayPlan;
import com.example.loan.exception.ContractIllegalException;
import com.example.loan.exception.PlanIllegalException;
import com.example.loan.service.RepayPlanService;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan/plan")
public class RepayPlanController {
    @Autowired
    private RepayPlanService repayPlanService;

    @PostMapping
    public ResponseResult<Integer> autoComplete(@RequestBody RepayPlan repayPlan){
        if(repayPlanService.autoComplete(repayPlan)){
            return ResponseResult.success(200, "success", repayPlan.getId());
        }else{
            throw new PlanIllegalException();
        }
    }
}
