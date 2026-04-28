package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class ApplyIllegalException extends RuntimeException{
    private int code=400;
    private String msg="填写申请数据非法，请重新填写！";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
