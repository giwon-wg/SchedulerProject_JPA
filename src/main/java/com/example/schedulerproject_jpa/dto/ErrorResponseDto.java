package com.example.schedulerproject_jpa.dto;

import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private final int status;
    private final String code;
    private final String message;

    public ErrorResponseDto(ErrorCode errorCode){
        this.status = errorCode.getStatus().value();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
