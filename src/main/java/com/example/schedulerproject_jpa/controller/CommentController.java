package com.example.schedulerproject_jpa.controller;


import com.example.schedulerproject_jpa.dto.CommentDelRequestDto;
import com.example.schedulerproject_jpa.dto.CommentRequestDto;
import com.example.schedulerproject_jpa.dto.CommentResponseDto;
import com.example.schedulerproject_jpa.dto.CommentUpdateDto;
import com.example.schedulerproject_jpa.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody @Valid CommentRequestDto dto, HttpSession session){
        CommentResponseDto commentResponseDto = commentService.createComment(dto, session);
        return ResponseEntity.ok(commentResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComment(@RequestParam Long scheduleId){
        List<CommentResponseDto> comment = commentService.getCommentByScheduleId(scheduleId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody @Valid CommentUpdateDto dto, HttpSession session){
        CommentResponseDto updated = commentService.updateComment(id, dto, session);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestBody(required = false) @Valid CommentDelRequestDto dto, HttpSession session){
        commentService.deleteComment(id, dto, session);
        return ResponseEntity.noContent().build();
    }
}
