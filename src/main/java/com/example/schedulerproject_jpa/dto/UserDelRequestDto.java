package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDelRequestDto {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
