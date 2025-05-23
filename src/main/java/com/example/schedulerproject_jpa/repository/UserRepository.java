package com.example.schedulerproject_jpa.repository;

import com.example.schedulerproject_jpa.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void delete(User user);
    Optional<User> findByEmail(String email);
}
