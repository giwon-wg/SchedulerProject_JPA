package com.example.schedulerproject_jpa.service;

import com.example.schedulerproject_jpa.authentication.PasswordVerifier;
import com.example.schedulerproject_jpa.config.PasswordEncoder;
import com.example.schedulerproject_jpa.dto.CommentDelRequestDto;
import com.example.schedulerproject_jpa.dto.CommentRequestDto;
import com.example.schedulerproject_jpa.dto.CommentResponseDto;
import com.example.schedulerproject_jpa.dto.CommentUpdateDto;
import com.example.schedulerproject_jpa.entity.Comment;
import com.example.schedulerproject_jpa.entity.Schedule;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.exception.CustomException;
import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import com.example.schedulerproject_jpa.repository.CommentRepository;
import com.example.schedulerproject_jpa.repository.ScheduleRepository;
import com.example.schedulerproject_jpa.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordVerifier passwordVerifier;

    /** 댓글 작성 */
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto dto, HttpSession session){
        Schedule schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        String authorName;
        String encodedPassword = null;

        Object loginUserId = session.getAttribute("loginUser");

        if (loginUserId != null && loginUserId instanceof Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            authorName = user.getUserName();
        } else {
            authorName = "익명";
            if (dto.getPassword() == null || dto.getPassword().isBlank()) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
            }
            encodedPassword = passwordEncoder.encode(dto.getPassword());
        }

        Comment comment = new Comment(dto.getComment(), authorName, encodedPassword, schedule);
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getComment(), comment.getAuthorName(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    /** 댓글 조회 */
    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentByScheduleId(Long scheduleId, Pageable pageable){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        return commentRepository.findByScheduleIdPaging(scheduleId, pageable).map(c -> new CommentResponseDto(c.getId(), c.getComment(), c.getAuthorName(), c.getCreatedAt(), c.getModifiedAt()));
    }

    /** 댓글 수정 */
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateDto dto, HttpSession session){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        Object loginUserId = session.getAttribute("loginUser");

        if (loginUserId != null && loginUserId instanceof Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            if (!user.getUserName().equals(comment.getAuthorName())) {
                throw new CustomException(ErrorCode.NOT_COMMENT_OWNER);
            }
        } else {
            if (dto == null || dto.getPassword() == null || dto.getPassword().isBlank()) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
            }
            passwordVerifier.verify(dto.getPassword(), comment.getPassword());
        }

        comment.updateComment(dto.getComment());

        return new CommentResponseDto(comment.getId(), comment.getComment(), comment.getAuthorName(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    /** 댓글 삭제 */
    @Transactional
    public void deleteComment(Long commentId, CommentDelRequestDto dto, HttpSession session){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        Object loginUserId = session.getAttribute("loginUser");

        if (loginUserId != null && loginUserId instanceof Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            if (!user.getUserName().equals(comment.getAuthorName())) {
                throw new CustomException(ErrorCode.NOT_COMMENT_OWNER);
            }
        } else {
            if (dto == null || dto.getPassword() == null || dto.getPassword().isBlank()) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
            }
            passwordVerifier.verify(dto.getPassword(), comment.getPassword());
        }

        commentRepository.delete(comment);
    }
}
