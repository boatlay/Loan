package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class ContractIllegalException extends RuntimeException {
    private int code=400;
    private String msg="生成合同所需的数据非法，请重试！";

    public <T> ResponseResult<T> error(T data){
        return ResponseResult.error(msg,code,data);
    }
}
