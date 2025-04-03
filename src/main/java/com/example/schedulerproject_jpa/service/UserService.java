package com.example.schedulerproject_jpa.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.schedulerproject_jpa.authentication.PasswordVerifier;
import com.example.schedulerproject_jpa.config.PasswordEncoder;
import com.example.schedulerproject_jpa.dto.UserRequestDto;
import com.example.schedulerproject_jpa.dto.UserResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.exception.CustomException;
import com.example.schedulerproject_jpa.exception.exceptioncode.ErrorCode;
import com.example.schedulerproject_jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordVerifier passwordVerifier;

    /** 유저 작성 */
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUserName(), dto.getEmail(), hashPassword);
        User saved = userRepository.save(user);
        return new UserResponseDto(saved);
    }

    /** 유저 조회 */
    @Transactional
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return new UserResponseDto(user);
    }

    /** 유저 전체 조회 */
    @Transactional
    public List<UserResponseDto> getAllUser() {
        return userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    /** 유저 수정 */
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        passwordVerifier.verify(dto.getPassword(), user.getPassword());

        String hashPassword = BCrypt.withDefaults().hashToString(12, dto.getPassword().toCharArray());
        user.update(dto.getUserName(), dto.getEmail(), hashPassword);
        return new UserResponseDto(user);
    }

    /** 유저 삭제 */
    @Transactional
    public void deleteUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        passwordVerifier.verify(dto.getPassword(), user.getPassword());
        userRepository.delete(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        return user;
    }

    @Transactional(readOnly = true)
    public User findUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
