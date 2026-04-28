package com.example.loan.controller;

import com.example.loan.service.RiskModelExerciseService;
import com.example.loan.utils.JwtUtils;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskModelExerciseController {

    @Autowired
    private RiskModelExerciseService riskModelExerciseService;

    @GetMapping("/calculate_score")
    public ResponseResult<Void> calculateScore(@RequestHeader String token){
        String name= JwtUtils.getNameFromJwt(token);
        return riskModelExerciseService.calculateScore(name);
    }
}
