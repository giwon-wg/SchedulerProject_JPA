package com.example.schedulerproject_jpa.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword){
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encdedPassword){
        return BCrypt.verifyer().verify(rawPassword.toCharArray(), encdedPassword).verified;
    }
}
