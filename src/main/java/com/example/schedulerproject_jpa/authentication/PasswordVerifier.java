package com.example.schedulerproject_jpa.authentication;

import com.example.schedulerproject_jpa.config.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordVerifier {

    private final PasswordEncoder passwordEncoder;

    public PasswordVerifier(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    public void verify(String inputPassword, String dbPassword){
        if(!passwordEncoder.matches(inputPassword, dbPassword)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
