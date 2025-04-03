package com.example.schedulerproject_jpa.exception.custom;

import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;

public class UserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public UserNotFoundException(){
        super(ErrorCode.USER_NOT_FOUND.getMsg());
        this.errorCode = ErrorCode.USER_NOT_FOUND;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
