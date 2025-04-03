package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDelRequestDto {

    @NotBlank(message = "작성 시 입력한 비밀번호를 입력하세요")
    private String password;

}
