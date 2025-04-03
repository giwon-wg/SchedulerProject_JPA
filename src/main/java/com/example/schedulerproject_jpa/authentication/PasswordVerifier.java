package com.example.schedulerproject_jpa.authentication;

import com.example.schedulerproject_jpa.config.PasswordEncoder;
import com.example.schedulerproject_jpa.exception.CustomException;
import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PasswordVerifier {

    private final PasswordEncoder passwordEncoder;

    public PasswordVerifier(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public void verify(String inputPassword, String dbPassword){
        if(!passwordEncoder.matches(inputPassword, dbPassword)){
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
