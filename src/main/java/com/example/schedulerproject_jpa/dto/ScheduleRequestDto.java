package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {

    @NotBlank(message = "작성자 정보는 필수입니다.")
    private String user;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String todo;

}
