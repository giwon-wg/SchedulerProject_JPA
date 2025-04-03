package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository{

    private final EntityManager em;

    /** 댓글 저장 */
    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    /** 댓글 일정Id기반 조회 */
    @Override
    public List<Comment> findByScheduleId(Long scheduleId) {
        return em.createQuery("select c from Comment c where c.schedule.id = :scheduleId", Comment.class).setParameter("scheduleId", scheduleId).getResultList();
    }

    @Override
    public Page<Comment> findByScheduleIdPaging(Long scheduleId, Pageable pageable) {
        List<Comment> comments = em.createQuery("select c from Comment c where c.schedule.id = :scheduleId order by c.createdAt asc", Comment.class).setParameter("scheduleId", scheduleId).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long count = em.createQuery("select count(c) from Comment c where c.schedule.id = :scheduleId", Long.class).setParameter("scheduleId", scheduleId).getSingleResult();

        return  new PageImpl<>(comments, pageable, count);
    }

    /** 댓글 전체 조회 */
    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    /** 댓글 조회 _ 페이징 적용 */


    /** 댓글 삭제 */
    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }
}
