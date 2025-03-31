package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule save(Schedule schedule);

    Optional<Schedule> findById(Long id);

    List<Schedule> findAll();

    void delete(Schedule schedule);
}
