package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    @NotNull(message = "일정 ID는 필수입니다.")
    private Long scheduleId;

    @NotNull(message = "댓글 내용은 필수입니다.")
    private String comment;

    private String password;
}
