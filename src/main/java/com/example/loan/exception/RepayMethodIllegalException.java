package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class RepayMethodIllegalException extends RuntimeException{
    private int code=400;
    private String msg="还款方式不匹配或不存在！";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
