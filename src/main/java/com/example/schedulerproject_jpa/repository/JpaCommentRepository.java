package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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

    /** 댓글 전체 조회 */
    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    /** 댓글 삭제 */
    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }
}
