package com.example.schedulerproject_jpa.controller;

import com.example.schedulerproject_jpa.authentication.LoginSession;
import com.example.schedulerproject_jpa.dto.ScheduleRequestDto;
import com.example.schedulerproject_jpa.dto.ScheduleResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.service.ScheduleService;
import com.example.schedulerproject_jpa.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    //일정 생성 로그인 유저만 사용가능
    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody @Valid ScheduleRequestDto dto, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);

        return scheduleService.createSchedule(dto, loginUser);
    }

    //일정 전체 조회
    @GetMapping
    public List<ScheduleResponseDto> getAllSchedule(){
        return scheduleService.getAllSchedules();
    }

    //개별 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id){
        return scheduleService.getSchedule(id);
    }


    //일정 수정 작성자만 수정 가능
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto dto, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        return scheduleService.updateSchedule(id, dto, loginUser);
    }

    //일정 삭제 작성자만 삭제 가능
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        scheduleService.deleteSchedule(id, loginUser);
    }
}