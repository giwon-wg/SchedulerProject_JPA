package com.example.schedulerproject_jpa.dto;

import com.example.schedulerproject_jpa.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String userName;
    private Long userId;
    private String title;
    private String todo;
    private LocalDateTime scheduleCreatedAt;
    private LocalDateTime scheduleModifiedAt;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.userName = schedule.getUser().getUserName();
        this.userId = schedule.getUser().getId();
        this.title = schedule.getTitle();
        this.todo = schedule.getTodo();
        this.scheduleCreatedAt = schedule.getCreatedAt();
        this.scheduleModifiedAt = schedule.getModifiedAt();
    }
}
