package com.example.schedulerproject_jpa.exception.custom;

import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;

public class ScheduleNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public ScheduleNotFoundException(){
        super(ErrorCode.SCHEDULE_NOT_FOUND.getMsg());
        this.errorCode = ErrorCode.SCHEDULE_NOT_FOUND;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
