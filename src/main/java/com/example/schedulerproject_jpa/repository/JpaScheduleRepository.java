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

    /** 일정 저장 */
    @Override
    public Schedule save(Schedule schedule) {
        em.persist(schedule);
        return schedule;
    }

    /** 일정 조회 */
    @Override
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(em.find(Schedule.class, id));
    }

    /** 일정 전체 조회 */
    @Override
    public List<Schedule> findAll() {
        return em.createQuery("SELECT s FROM Schedule s", Schedule.class).getResultList();
    }

    /** 일정 삭제 */
    @Override
    public void delete(Schedule schedule) {
        em.remove(schedule);
    }

}
