package com.example.loan.exception;

import com.example.loan.utils.ResponseResult;

public class UserDataIncompleteException extends RuntimeException {
    private int code = 400;
    private String msg = "用户信息不完整，请先完善资料";

    public <T> ResponseResult<T> error(T data) {
        return ResponseResult.error(msg, code, data);
    }
}