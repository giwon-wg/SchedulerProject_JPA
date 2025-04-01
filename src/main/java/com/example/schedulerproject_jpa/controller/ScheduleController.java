package com.example.schedulerproject_jpa.controller;


import com.example.schedulerproject_jpa.dto.ScheduleRequestDto;
import com.example.schedulerproject_jpa.dto.ScheduleResponseDto;
import com.example.schedulerproject_jpa.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    //일정 생성
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody @Valid ScheduleRequestDto dto){
        return scheduleService.createSchedule(dto);
    }

    //전체 일정 조회
    @GetMapping
    public List<ScheduleResponseDto> getAllSchedule(){
        return scheduleService.getAllSchedules();
    }

    //개별 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }

    //일정 수정
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto dto){
        return scheduleService.updateSchedule(id, dto);
    }

    //일정 삭제
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto dto){
        scheduleService.deleteSchedule(id, dto);
    }
}
