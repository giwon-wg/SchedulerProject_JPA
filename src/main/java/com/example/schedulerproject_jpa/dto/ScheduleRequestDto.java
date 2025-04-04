package com.example.schedulerproject_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 10, message = "제목은 10자 이하로 작성해 주세요.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String todo;

}
