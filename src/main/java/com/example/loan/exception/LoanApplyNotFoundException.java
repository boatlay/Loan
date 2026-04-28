package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class LoanApplyNotFoundException extends RuntimeException{
    private int code = 404;
    private String msg = "你还没有贷过款";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
