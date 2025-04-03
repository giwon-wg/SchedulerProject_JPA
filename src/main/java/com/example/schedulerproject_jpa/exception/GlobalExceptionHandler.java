package com.example.schedulerproject_jpa.exception;

import com.example.schedulerproject_jpa.dto.ErrorResponseDto;
import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** @Valid 유효성 검사 실패 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus()).body(new ErrorResponseDto(ErrorCode.INVALID_INPUT_VALUE.getStatus().value(), ErrorCode.INVALID_INPUT_VALUE.getCode(), errorMsg));
    }

    /** 잘못된 HTTP 요청 방식 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String methods = e.getSupportedHttpMethods().stream().map(HttpMethod::name).collect(Collectors.joining(", "));
        String msg = "지원하지 않는 HTTP 메서드입니다. 허용: " + methods;
        return ResponseEntity.status(ErrorCode.METHOD_NOT_ALLOWED.getStatus()).body(new ErrorResponseDto(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value(), ErrorCode.METHOD_NOT_ALLOWED.getCode(), msg));
    }

    /** JSON 파싱 오류 */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponseDto> handleConversion(HttpMessageConversionException e) {
        return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus()).body(new ErrorResponseDto(ErrorCode.INVALID_INPUT_VALUE));
    }

    /** DB 제약 조건 위반 (ex. 이메일 중복) */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrity(DataIntegrityViolationException e) {
        if (e.getMessage() != null && e.getMessage().toLowerCase().contains("email")) {
            return ResponseEntity.status(ErrorCode.DUPLICATED_EMAIL.getStatus()).body(new ErrorResponseDto(ErrorCode.DUPLICATED_EMAIL));
        }

        return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getStatus()).body(new ErrorResponseDto(ErrorCode.INVALID_INPUT_VALUE));
    }

    /** 사용자 정의 예외 */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new ErrorResponseDto(e.getErrorCode()));
    }

//    /** 그 외 모든 예외 */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDto> handleAll(Exception e) {
//        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR));
//    }
}
