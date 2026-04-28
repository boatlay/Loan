package com.example.loan.controller;

import com.example.loan.dao.entity.RepayOverdueRecord;
import com.example.loan.dao.entity.RepayPlan;
import com.example.loan.dao.entity.RepayRecord;
import com.example.loan.exception.RepayException;
import com.example.loan.exception.RepayOverdueRecordNotFoundException;
import com.example.loan.exception.RepayPlanNotFoundException;
import com.example.loan.exception.RepayRecordNotFoundException;
import com.example.loan.service.RepayOverdueRecordService;
import com.example.loan.service.RepayPlanService;
import com.example.loan.service.RepayRecordService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repay")
public class RepayController {
    @Autowired
    private RepayRecordService repayRecordService;

    @Autowired
    private RepayOverdueRecordService repayOverdueRecordService;

    @Autowired
    private RepayPlanService repayPlanService;

    @GetMapping
    public ResponseResult<List<RepayPlan>> getPlan(@RequestHeader String token){
        //...
        String name= JwtUtils.getNameFromJwt(token);

        List<RepayPlan> list = repayPlanService.getPlanByName(name);
        if(list.size() != 0){
            return ResponseResult.success(200, "success", list);
        }else{
            throw new RepayPlanNotFoundException();
        }
    }

    @PostMapping
    public ResponseResult<Float> repay(@RequestBody Map<String, Object> params){
        //按参数名提取JSON中的值，手动转换类型
        Integer id = (Integer) params.get("id");
        String repayMethod = (String) params.get("repayMethod");

        //按id取得repayPlan
        RepayPlan repayPlan = repayPlanService.getPlanById(id);

        if(repayPlanService.repay(repayPlan, repayMethod) == true){
            return ResponseResult.success(200, "success", repayPlan.getPriIntAll());
        }else{
            throw new RepayException();
        }
    }

    @GetMapping("/history")
    public ResponseResult<List> getRecord(@RequestHeader String token){
//        HttpSession session = httpServletRequest.getSession();
//        String name = (String) session.getAttribute("name");
        String name= JwtUtils.getNameFromJwt(token);

        List<RepayRecord> list = repayRecordService.getRecordByName(name);
        if(list.size() != 0){
            return ResponseResult.success(200, "success", list);
        }else{
            throw new RepayRecordNotFoundException();
        }
    }

    @GetMapping("/overdue")
    public ResponseResult<List> getOverdueRecord(@RequestHeader String token){
//        HttpSession session = httpServletRequest.getSession();
//        String name = (String) session.getAttribute("name");
        String name= JwtUtils.getNameFromJwt(token);

        List<RepayOverdueRecord> list = repayOverdueRecordService.getOverdueRecordByName(name);

        if(list.size() != 0){
            return ResponseResult.success(200, "success", list);
        }else{
            throw new RepayOverdueRecordNotFoundException();
        }
    }
}
