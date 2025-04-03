package com.example.schedulerproject_jpa.exception;

import com.example.schedulerproject_jpa.dto.ErrorResponseDto;
import com.example.schedulerproject_jpa.exception.custom.PasswordMismatchException;
import com.example.schedulerproject_jpa.exception.custom.ScheduleNotFoundException;
import com.example.schedulerproject_jpa.exception.custom.UnauthorizedAccessException;
import com.example.schedulerproject_jpa.exception.custom.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //@Valid 유효성 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e){
        String errorMsg = e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ErrorResponseDto("BAD_REQUEST", errorMsg));
    }

    //DB 조건 위반 예외
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrity(DataIntegrityViolationException e){
        String msg = e.getMessage();

        if(msg != null && msg.toLowerCase().contains("email")){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("BAD_REQUEST", "이미 존재하는 이메일 입니다."));
        }

        return ResponseEntity.badRequest().body(new ErrorResponseDto("BAD_REQUEST", "데이터 무결성 오류"));
    }

    //Http 요청 예외
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequest(HttpRequestMethodNotSupportedException e){
        String errorMsg = e.getSupportedHttpMethods().stream().map(HttpMethod::name).collect(Collectors.joining(","));
        String message = "지원하지 않는 HTTP 메서드. 사용가능한 메서드는: " + errorMsg;
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponseDto("METHOD_NOT_ALLOWED", message));
    }

    //JSON 파싱 예외
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageParse(HttpMessageConversionException e){
        return ResponseEntity.badRequest().body(new ErrorResponseDto("BAD_REQUEST", "잘못된 요청 본문"));
    }

    //커스텀 예외 처리
    /** 유저 찾지 못함 */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException e){
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new ErrorResponseDto("USER_NOT_FOUND", e.getMessage()));
    }

    /** 일정 찾지 못함 */
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleScheduleNotFound(ScheduleNotFoundException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponseDto("SCHEDULE_NOT_FOUND", e.getMessage()));
    }

    /**  */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedAccessException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponseDto("UNAUTHORIZED", e.getMessage()));
    }

    /** 비밀번호 틀림 */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordMismatch(PasswordMismatchException e){
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponseDto("PASSWORD_MISMATCH", e.getMessage()));
    }
}
