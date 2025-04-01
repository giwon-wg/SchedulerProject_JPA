package com.example.schedulerproject_jpa.service;

import com.example.schedulerproject_jpa.dto.ScheduleRequestDto;
import com.example.schedulerproject_jpa.dto.ScheduleResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto){
        //userId로 User 객체를 DB에서 조회
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("작성자 정보를 찾을 수 없습니다."));


        //User 객체를 넣어 Schedule 객체 생성
        Schedule schedule = new Schedule(
                user,
                dto.getTitle(),
                dto.getTodo()
        );

        //저장 후 DTO로 변환
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
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        schedule.update(dto.getTitle(), dto.getTodo());
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        scheduleRepository.delete(schedule);
    }
}
