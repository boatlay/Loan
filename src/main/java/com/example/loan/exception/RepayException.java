package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class RepayException extends RuntimeException{
    private int code=400;
    private String msg="支付失败，请重试！";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
