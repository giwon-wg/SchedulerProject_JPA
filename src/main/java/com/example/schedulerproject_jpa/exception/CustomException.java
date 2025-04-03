package com.example.schedulerproject_jpa.exception;

import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends  RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
