package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class RepayRecordNotFoundException extends RuntimeException{
    private int code = 404;
    private String msg = "你还没有还款记录";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
