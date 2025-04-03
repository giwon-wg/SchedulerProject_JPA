package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByScheduleId(Long scheduleId);
    Page<Comment> findByScheduleIdPaging(Long scheduleId, Pageable pageable);
    Optional<Comment> findById(Long id);
    void delete(Comment comment);
}
