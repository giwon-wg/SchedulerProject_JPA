package com.example.schedulerproject_jpa.exception.custom;

import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;

public class UnauthorizedAccessException extends RuntimeException{
    private final ErrorCode errorCode;

    public UnauthorizedAccessException(){
        super(ErrorCode.UNAUTHORIZED.getMsg());
        this.errorCode = ErrorCode.UNAUTHORIZED;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
