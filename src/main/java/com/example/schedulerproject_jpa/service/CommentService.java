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
import com.example.schedulerproject_jpa.repository.CommentRepository;
import com.example.schedulerproject_jpa.repository.ScheduleRepository;
import com.example.schedulerproject_jpa.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Schedule schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        String authorName;
        String encodedPassword = null;

        Object loginUserId = session.getAttribute("loginUser");

        if(loginUserId != null && loginUserId instanceof Long userId){
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            authorName = user.getUserName();
        } else {
            authorName = "익명";
            if (dto.getPassword() == null && dto.getPassword().isBlank()){
                throw new IllegalArgumentException("비회원은 비밀번호를 입력해야합니다.");
            }
            encodedPassword = passwordEncoder.encode(dto.getPassword());
        }

        Comment comment = new Comment(dto.getComment(), authorName, encodedPassword, schedule);
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getComment(), comment.getAuthorName(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    /** 댓글 조회 */
    @Transactional
    public List<CommentResponseDto> getCommentByScheduleId(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(()-> new IllegalArgumentException("일정이 존재하지 않습니다."));

        return commentRepository.findByScheduleId(scheduleId).stream().map(c -> new CommentResponseDto(c.getId(), c.getComment(), c.getAuthorName(), c.getCreatedAt(), c.getModifiedAt())).toList();
    }

    /** 댓글 수정 */
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateDto dto, HttpSession session){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        Object loginUserId = session.getAttribute("loginUser");
        if(loginUserId != null && loginUserId instanceof Long userId){
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            if(!user.getUserName().equals(comment.getAuthorName())){
                throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
            }
        } else {
            if (dto == null || dto.getPassword() == null || dto.getPassword().isBlank()){
                throw new IllegalArgumentException("비회원은 비밀번호를 입력해야합니다.");
            }
            passwordVerifier.verify(dto.getPassword(), comment.getPassword());
        }
        comment.updateComment(dto.getComment());

        return new CommentResponseDto(comment.getId(), comment.getComment(), comment.getAuthorName(), comment.getCreatedAt(), comment.getModifiedAt());
    }

    /** 댓글 삭제 */
    @Transactional
    public void deleteComment(Long commentId, CommentDelRequestDto dto, HttpSession session){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        //로그인하면 유저명으로 안하면 익명으로..
        Object loginUserId = session.getAttribute("loginUser");
        if(loginUserId != null && loginUserId instanceof Long userId){
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            if(!user.getUserName().equals(comment.getAuthorName())){
               throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
            }
        } else {
            if (dto == null || dto.getPassword() == null || dto.getPassword().isBlank()){
                throw new IllegalArgumentException("비회원은 비밀번호를 입력해야합니다.");
            }
            passwordVerifier.verify(dto.getPassword(), comment.getPassword());
        }
        commentRepository.delete(comment);
    }
}
