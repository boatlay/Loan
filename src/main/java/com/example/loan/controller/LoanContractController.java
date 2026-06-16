package com.example.loan.controller;

import com.example.loan.dao.entity.LoanContract;
import com.example.loan.exception.ContractIllegalException;
import com.example.loan.service.LoanContractService;
import com.example.loan.utils.ResponseResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 下载合同（根据申请ID）
     * @param applyId
     * @param response
     */
    @GetMapping("/download")
    public ResponseResult download(@RequestParam Integer applyId, HttpServletResponse response) {
        loanContractService.downloadContract(applyId, response);
        return ResponseResult.success();
    }
}
