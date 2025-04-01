package com.example.schedulerproject_jpa.dto;

import com.example.schedulerproject_jpa.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime userCreatedAt;
    private LocalDateTime userModifiedAt;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.userCreatedAt = user.getCreatedAt();
        this.userModifiedAt = user.getModifiedAt();
    }
}
