package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class RepayOverdueRecordNotFoundException extends RuntimeException{
    private int code = 404;
    private String msg = "你还没有逾期记录";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
