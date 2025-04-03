package com.example.schedulerproject_jpa.filter;

import com.example.schedulerproject_jpa.dto.ErrorResponseDto;
import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.Filter;

public class LoginCheckFilter implements Filter {

    private static final List<String> whiteList = List.of(
            "/api/users/login",
            "/api/users",
            "/api/schedules",
            "/api/comments"
            );

    private boolean isWhiteList(String uri, String method){
        //스케줄조회
        if(uri.equals("/api/schedules") && method.equals("GET")){
            return true;
        }

        //스케줄 조회
        if(uri.startsWith("/api/schedules") && method.equals("GET")){
            return true;
        }

        //댓글 삭제
        if(uri.matches("/api/comments/\\d+$") && method.equals("DELETE")){
            return true;
        }

        //댓글 수정
        if(uri.matches("/api/comments/\\d+$") && method.equals("PUT")){
            return true;
        }
        return whiteList.contains(uri);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rs = (HttpServletResponse) response;

        String requestURI = rq.getRequestURI();
        String method = rq.getMethod();

        if(isWhiteList(requestURI, method)){
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = rq.getSession(false);

        if(session == null || session.getAttribute("loginUser") == null){

            ErrorCode errorCode = ErrorCode.LOGIN_REQUIRED;
            ErrorResponseDto errorResponsedto = new ErrorResponseDto(errorCode);

            rs.setStatus(errorCode.getStatus().value());
            rs.setContentType("application/json;charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(errorResponsedto);

            rs.getWriter().write(json);
            return;
        }
        chain.doFilter(request, response);
    }
}
