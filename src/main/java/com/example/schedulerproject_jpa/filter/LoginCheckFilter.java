package com.example.schedulerproject_jpa.filter;

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

    private static final List<String> whiteList = List.of("/api/users/login", "/api/users");

    private boolean isWhiteList(String uri){
        return whiteList.stream().anyMatch(uri::equals);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rs = (HttpServletResponse) response;

        String requestURI = rq.getRequestURI();

        if(isWhiteList(requestURI)){
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = rq.getSession(false);

        if(session == null || session.getAttribute("loginUser") == null){
            rs.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            rs.setContentType("application/json");
            rs.setContentType("application/json; charset=UTF-8");
            rs.getWriter().write("{\"msg\": \"회원만 사용 가능합니다.\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
