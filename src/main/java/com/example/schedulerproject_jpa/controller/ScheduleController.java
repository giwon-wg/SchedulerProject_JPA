package com.example.schedulerproject_jpa.controller;

import com.example.schedulerproject_jpa.authentication.LoginSession;
import com.example.schedulerproject_jpa.dto.ErrorResponseDto;
import com.example.schedulerproject_jpa.dto.ScheduleRequestDto;
import com.example.schedulerproject_jpa.dto.ScheduleResponseDto;
import com.example.schedulerproject_jpa.entity.User;
import com.example.schedulerproject_jpa.service.ScheduleService;
import com.example.schedulerproject_jpa.service.UserService;
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
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    /**일정 생성 로그인 유저만 사용가능 */
    @Operation(summary = "일정 생성", description = "로그인된 사용자가 새로운 일정 추가 가능")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "일정 생성 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 본문 형식 또는 유효성 오류", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        ScheduleResponseDto created = scheduleService.createSchedule(dto, loginUser);
        return ResponseEntity.status(201).body(created);
    }
    /**일정 전체 조회 */
    @Operation(summary = "일정 단건 조회", description = "ID로 일정을 조회합니다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "해당 일정 ID가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ScheduleResponseDto>> getAllSchedule(@PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC)Pageable pageable){

        return ResponseEntity.ok(scheduleService.getAllSchedules(pageable));
    }

    /**개별 일정 조회 */
    @Operation(summary = "전체 일정 조회", description = "모든 일정을 페이지로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정상 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id){
        return ResponseEntity.ok(scheduleService.getSchedule(id));
    }


    /**일정 수정 작성자만 수정 가능 */
    @Operation(summary = "일정 수정", description = "작성자 본인만 수정 가능합니다.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "정상 수정"),
            @ApiResponse(responseCode = "401", description = "로그인하지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "작성자가 아닌 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "일정 ID 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid ScheduleRequestDto dto, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        ScheduleResponseDto updated = scheduleService.updateSchedule(id, dto, loginUser);
        return ResponseEntity.ok(updated);
    }

    /**일정 삭제 작성자만 삭제 가능 */
    @Operation(summary = "일정 삭제", description = "작성자 본인만 삭제할 수 있습니다.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "정상 삭제"),
            @ApiResponse(responseCode = "401", description = "로그인하지 않은 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "작성자가 아닌 사용자", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "일정 ID 없음", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpSession session){
        Long userId = LoginSession.getLoginUserId(session);
        User loginUser = userService.findUserId(userId);
        scheduleService.deleteSchedule(id, loginUser);
        return ResponseEntity.noContent().build();
    }
}