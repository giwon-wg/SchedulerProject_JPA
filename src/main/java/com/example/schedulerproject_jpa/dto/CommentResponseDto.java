package com.example.schedulerproject_jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String comment;
    private String authorName;
    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentModifiedAt;
}
