package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        return em.createQuery("SELECT s FROM Schedule s", Schedule.class)
                .getResultList();
    }

    /** 일정 전체 조회_ 페이징 적용 */
    @Override
    public Page<Schedule> findAllPaging(Pageable pageable) {
        List<Schedule> schedules = em.createQuery("SELECT s from Schedule s ORDER BY s.modifiedAt desc", Schedule.class).setFirstResult((int)pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long total = em.createQuery("select count(s) from Schedule s", Long.class).getSingleResult();
        return new PageImpl<>(schedules, pageable, total);
    }

    /** 일정 삭제 */
    @Override
    public void delete(Schedule schedule) {
        em.remove(schedule);
    }

}
