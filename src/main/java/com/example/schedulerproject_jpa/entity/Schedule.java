package com.example.schedulerproject_jpa.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT", length = 200)
    private String todo;

    @Setter(AccessLevel.NONE)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Setter(AccessLevel.NONE)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Schedule(User user, String title, String todo){
        this.user = user;
        this.title = title;
        this.todo = todo;
    }

    public void update(String title, String todo){
        this.title = title;
        this.todo = todo;
    }
}
