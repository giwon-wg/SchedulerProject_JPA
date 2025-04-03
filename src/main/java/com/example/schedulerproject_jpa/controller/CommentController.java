package com.example.schedulerproject_jpa.controller;


import com.example.schedulerproject_jpa.dto.*;
import com.example.schedulerproject_jpa.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "댓글 작성", description = "댓글 작성 (비회원 작성 가능, 비밀번호 필수값)")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 오류 또는 일정 ID 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody @Valid CommentRequestDto dto, HttpSession session){
        CommentResponseDto commentResponseDto = commentService.createComment(dto, session);
        return ResponseEntity.ok(commentResponseDto);
    }

    /** 댓글 조회*/
    @Operation(summary = "댓글 목록 조회", description = "특정 일정에 대한 댓글 목록을 페이징으로 조회")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터 또는 일정 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getComment(@PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam Long scheduleId){
        Page<CommentResponseDto> comment = commentService.getCommentByScheduleId(scheduleId, pageable);
        return ResponseEntity.ok(comment);
    }

    /** 댓글 수정*/
    @Operation(summary = "댓글 수정", description = "댓글 수정 (비회원 비밀번호 필수)")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 또는 비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "댓글 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody @Valid CommentUpdateDto dto, HttpSession session){
        CommentResponseDto updated = commentService.updateComment(id, dto, session);
        return ResponseEntity.ok(updated);
    }

    /** 댓글 삭제*/
    @DeleteMapping("/{id}")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제 (비회원 비밀번호 필수)")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음 또는 비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "댓글 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestBody(required = false) @Valid CommentDelRequestDto dto, HttpSession session){
        commentService.deleteComment(id, dto, session);
        return ResponseEntity.noContent().build();
    }
}
