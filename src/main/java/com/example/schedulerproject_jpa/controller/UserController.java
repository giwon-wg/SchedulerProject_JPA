package com.example.schedulerproject_jpa.controller;

import com.example.schedulerproject_jpa.dto.UserRequestDto;
import com.example.schedulerproject_jpa.dto.UserResponseDto;
import com.example.schedulerproject_jpa.service.UserService;
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
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    //유저 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    //유저 전체 조회
    @GetMapping
    public  ResponseEntity<List<UserResponseDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    //유저 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto){
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    //유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
