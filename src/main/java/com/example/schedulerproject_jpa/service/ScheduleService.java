package com.example.schedulerproject_jpa.service;

import com.example.schedulerproject_jpa.dto.ScheduleRequestDto;
import com.example.schedulerproject_jpa.dto.ScheduleResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import org.springframework.transaction.annotation.Transactional;
import com.example.schedulerproject_jpa.entity.Schedule;
import com.example.schedulerproject_jpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, User loginUser){
        Schedule schedule = new Schedule(loginUser, dto.getTitle(), dto.getTodo());
        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        return new ScheduleResponseDto(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAllSchedules(){
        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto, User loginUser) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        if (!schedule.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        schedule.update(dto.getTitle(), dto.getTodo());
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, User loginUser){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        if (!schedule.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}
