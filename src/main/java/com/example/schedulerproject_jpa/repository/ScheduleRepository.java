package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    Schedule save(Schedule schedule);
    Optional<Schedule> findById(Long id);
    List<Schedule> findAll();
    Page<Schedule> findAllPaging(Pageable pageable);
    void delete(Schedule schedule);
}
