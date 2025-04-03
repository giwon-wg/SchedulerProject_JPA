package com.example.schedulerproject_jpa.controller;

import com.example.schedulerproject_jpa.authentication.LoginSession;
import com.example.schedulerproject_jpa.dto.ErrorResponseDto;
import com.example.schedulerproject_jpa.dto.LoginRequestDto;
import com.example.schedulerproject_jpa.dto.UserRequestDto;
import com.example.schedulerproject_jpa.dto.UserResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /** 회원가입 */
    @Operation(summary = "회원가입", description = "새로운 사용자 등록")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "입력값 검증 실패", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "중복된 이메일 존재", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    /**유저 단건 조회*/
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(HttpSession session) {
        Long userId = LoginSession.getLoginUserId(session);
        User user = userService.findUserId(userId);
        return ResponseEntity.ok(new UserResponseDto(user));
    }

    /**유저 전체 조회*/
    @GetMapping
    public  ResponseEntity<List<UserResponseDto>> getAllUser(HttpSession session){
        Long loginId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(loginId);
        List<UserResponseDto> result = userService.getAllUser(loginUser);
        return ResponseEntity.ok(result);
    }

    /**유저 수정*/
    @Operation(summary = "회원 정보 수정", description = "사용자의 정보를 수정합니다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "로그인하지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto dto, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        return ResponseEntity.ok(userService.updateUser(userId, dto));
    }

    /**유저 삭제*/
    @Operation(summary = "회원 탈퇴", description = "사용자를 삭제합니다.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "로그인하지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody UserRequestDto dto, HttpSession session){
        Long loginUser = LoginSession.getLoginUserId(session);

        if(!loginUser.equals(id)){
            throw new IllegalArgumentException("본인 정보만 수정 가능합니다.");
        }

        userService.deleteUser(id, dto);
        return ResponseEntity.noContent().build();
    }

    /** 로그인 */
    @Operation(summary = "로그인", description = "사용자 로그인 처리 (세션 저장)")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto, HttpServletRequest request){
        User user = userService.login(dto.getEmail(), dto.getPassword());
        request.getSession(true).setAttribute("loginUser", user.getId());
        return ResponseEntity.ok("로그인 되었습니다");
    }
}