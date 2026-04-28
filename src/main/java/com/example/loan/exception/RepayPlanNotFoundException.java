package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class RepayPlanNotFoundException extends RuntimeException{
    private int code = 404;
    private String msg = "你没有需要进行的还款";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
