package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByScheduleId(Long scheduleId);
    Optional<Comment> findById(Long id);
    void delete(Comment comment);
}
