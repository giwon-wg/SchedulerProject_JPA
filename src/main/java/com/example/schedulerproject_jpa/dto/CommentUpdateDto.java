package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String comment;

    private String password;
}
