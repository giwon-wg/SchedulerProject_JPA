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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    /**일정 생성 로그인 유저만 사용가능 */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        ScheduleResponseDto created = scheduleService.createSchedule(dto, loginUser);
        return ResponseEntity.status(201).body(created);
    }

    /**일정 전체 조회 */
    @GetMapping
    public ResponseEntity<Page<ScheduleResponseDto>> getAllSchedule(@PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC)Pageable pageable){

        return ResponseEntity.ok(scheduleService.getAllSchedules(pageable));
    }

    /**개별 일정 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id){
        return ResponseEntity.ok(scheduleService.getSchedule(id));
    }


    /**일정 수정 작성자만 수정 가능 */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto dto, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        ScheduleResponseDto updated = scheduleService.updateSchedule(id, dto, loginUser);
        return ResponseEntity.ok(updated);
    }

    /**일정 삭제 작성자만 삭제 가능 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        scheduleService.deleteSchedule(id, loginUser);
        return ResponseEntity.noContent().build();
    }
}