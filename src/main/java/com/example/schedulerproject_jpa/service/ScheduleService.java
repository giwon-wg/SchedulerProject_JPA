package com.example.schedulerproject_jpa.service;

import com.example.schedulerproject_jpa.dto.ScheduleRequestDto;
import com.example.schedulerproject_jpa.dto.ScheduleResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.exception.CustomException;
import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.example.schedulerproject_jpa.entity.Schedule;
import com.example.schedulerproject_jpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /** 일정 작성 */
    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, User loginUser){
        Schedule schedule = new Schedule(loginUser, dto.getTitle(), dto.getTodo());
        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(saved);
    }

    /** 일정 조회 */
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
        return new ScheduleResponseDto(schedule);
    }

    /** 일정 전체 조회 */
    @Transactional(readOnly = true)
    public Page<ScheduleResponseDto> getAllSchedules(Pageable pageable){
        return scheduleRepository.findAllPaging(pageable).map(ScheduleResponseDto::new);
    }

    /** 일정 업데이트 */
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto, User loginUser) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!schedule.getUser().getId().equals(loginUser.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        schedule.update(dto.getTitle(), dto.getTodo());
        return new ScheduleResponseDto(schedule);
    }

    /** 일정 삭제 */
    @Transactional
    public void deleteSchedule(Long id, User loginUser){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        if (!schedule.getUser().getId().equals(loginUser.getId())) {
            throw new CustomException(ErrorCode.NOT_SCHEDULE_OWNER);
        }

        scheduleRepository.delete(schedule);
    }
}
