package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {

    @NotNull(message = "유저 ID가 입력되지 않았습니다.")
    private Long userId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 10, message = "제목은 10자 이하로 작성해 주세요.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String todo;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

}
