package com.example.loan.controller;

import com.example.loan.dao.entity.LoanContract;
import com.example.loan.exception.ApplyIllegalException;
import com.example.loan.exception.ContractIllegalException;
import com.example.loan.service.LoanApplyService;
import com.example.loan.service.LoanContractService;
import com.example.loan.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan/contract")
public class LoanContractController {
    @Autowired
    private LoanContractService loanContractService;

    @PostMapping
    public ResponseResult<Integer> autoComplete(@RequestBody LoanContract loanContract){
        if(loanContractService.autoComplete(loanContract)){
            return ResponseResult.success(200, "success", loanContract.getId());
        }else{
            throw new ContractIllegalException();
        }
    }
}
