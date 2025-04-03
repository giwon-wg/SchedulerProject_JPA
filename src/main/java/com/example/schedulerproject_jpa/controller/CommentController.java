package com.example.schedulerproject_jpa.controller;


import com.example.schedulerproject_jpa.dto.CommentDelRequestDto;
import com.example.schedulerproject_jpa.dto.CommentRequestDto;
import com.example.schedulerproject_jpa.dto.CommentResponseDto;
import com.example.schedulerproject_jpa.dto.CommentUpdateDto;
import com.example.schedulerproject_jpa.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /** 댓글 작성*/
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody @Valid CommentRequestDto dto, HttpSession session){
        CommentResponseDto commentResponseDto = commentService.createComment(dto, session);
        return ResponseEntity.ok(commentResponseDto);
    }

    /** 댓글 조회*/
    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getComment(@PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam Long scheduleId){
        Page<CommentResponseDto> comment = commentService.getCommentByScheduleId(scheduleId, pageable);
        return ResponseEntity.ok(comment);
    }

    /** 댓글 수정*/
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody @Valid CommentUpdateDto dto, HttpSession session){
        CommentResponseDto updated = commentService.updateComment(id, dto, session);
        return ResponseEntity.ok(updated);
    }

    /** 댓글 삭제*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestBody(required = false) @Valid CommentDelRequestDto dto, HttpSession session){
        commentService.deleteComment(id, dto, session);
        return ResponseEntity.noContent().build();
    }
}
