package com.example.schedulerproject_jpa.controller;

import com.example.schedulerproject_jpa.authentication.LoginSession;
import com.example.schedulerproject_jpa.dto.LoginRequestDto;
import com.example.schedulerproject_jpa.dto.UserRequestDto;
import com.example.schedulerproject_jpa.dto.UserResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.service.UserService;
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

    //유저 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    //유저 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id, HttpSession session){
        Long loginUser = LoginSession.getLoginUserId(session);

        if(!loginUser.equals(id)){
            throw new IllegalArgumentException("본인 정보만 열람 가능합니다.");
        }

        return ResponseEntity.ok(userService.getUser(id));
    }

    //유저 전체 조회
    @GetMapping
    public  ResponseEntity<List<UserResponseDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    //유저 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto, HttpSession session){
        Long loginUser = LoginSession.getLoginUserId(session);

        if(!loginUser.equals(id)){
            throw new IllegalArgumentException("본인 정보만 수정 가능합니다.");
        }

        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    //유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestBody UserRequestDto dto, HttpSession session){
        Long loginUser = LoginSession.getLoginUserId(session);

        if(!loginUser.equals(id)){
            throw new IllegalArgumentException("본인 정보만 수정 가능합니다.");
        }

        userService.deleteUser(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto, HttpServletRequest request){
        User user = userService.login(dto.getEmail(), dto.getPassword());
        request.getSession(true).setAttribute("loginUser", user.getId());
        return ResponseEntity.ok("로그인 되었습니다");
    }
}