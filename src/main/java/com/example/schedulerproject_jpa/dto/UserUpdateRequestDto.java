package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @NotBlank(message = "유저명은 필수입니다.")
    @Size(max = 4, message = "유저명은 4자 이하로 작성하여 주세요")
    private String userName;

    @NotBlank(message = "이메일은 아이디로 활용됩니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,20}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,}$", message = "비밀번호는 영어 소문자와 숫자를 포함한 4자리 이상이여야 합니다.")
    private String password;

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,}$", message = "새 비밀번호는 영어 소문자와 숫자를 포함한 4자리 이상이여야 합니다.")
    private String newPassword;
}
