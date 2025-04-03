package com.example.schedulerproject_jpa.authentication;

import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.exception.CustomException;
import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import jakarta.servlet.http.HttpSession;

public class LoginSession {

    public static final String loginUserKey = "loginUser";

    //로그인 인증
    public static Long getLoginUserId(HttpSession session) {
        Object userId = session.getAttribute(loginUserKey);
        if (userId == null || !(userId instanceof Long)) {
            throw new CustomException(ErrorCode.LOGIN_REQUIRED);
        }
        return (Long) userId;
    }

    //비회원 인증
    public static Long getLoginUserIdOrNull(HttpSession session) {
        Object userId = session.getAttribute(loginUserKey);
        if (userId instanceof Long id) {
            return id;
        }
        return null;
    }
}
