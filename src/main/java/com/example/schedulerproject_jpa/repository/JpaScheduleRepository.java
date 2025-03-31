package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaScheduleRepository implements ScheduleRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Schedule save(Schedule schedule) {
        em.persist(schedule);
        return schedule;
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(em.find(Schedule.class, id));
    }

    @Override
    public List<Schedule> findAll() {
        return em.createQuery("SELECT s FROM Schedule s", Schedule.class).getResultList();
    }

    @Override
    public void delete(Schedule schedule) {
        em.remove(schedule);
    }

}
