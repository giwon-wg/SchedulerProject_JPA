package com.example.schedulerproject_jpa.repository;


import com.example.schedulerproject_jpa.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    /** 유저 저장 */
    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    /** 유저 조회 */
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    /** 유저 전체 조회 */
    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u From User u", User.class).getResultList();
    }

    /** 유저 삭제 */
    @Override
    public void delete(User user) {
        em.remove(user);
    }

    /** 유저 Email 찾기 */
    @Override
    public Optional<User> findByEmail(String email) {
        return em.createQuery("SELECT u FROM User u where u.email = :email", User.class).setParameter("email", email).getResultList().stream().findFirst();
    }
}
