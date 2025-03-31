package com.example.schedulerproject_jpa.dto;

import com.example.schedulerproject_jpa.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String user;
    private String title;
    private String todo;
    private LocalDateTime scheduleCreatedAt;
    private LocalDateTime scheduleModifiedAt;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.user = schedule.getUser();
        this.title = schedule.getTitle();
        this.todo = schedule.getTodo();
        this.scheduleCreatedAt = schedule.getCreatedAt();
        this.scheduleModifiedAt = schedule.getModifiedAt();
    }
}
