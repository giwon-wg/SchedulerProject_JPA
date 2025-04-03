package com.example.schedulerproject_jpa.exception.custom;

import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;

public class PasswordMismatchException extends RuntimeException{
    private final ErrorCode errorCode;

    public PasswordMismatchException(){
        super(ErrorCode.PASSWORD_MISMATCH.getMsg());
        this.errorCode = ErrorCode.PASSWORD_MISMATCH;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
