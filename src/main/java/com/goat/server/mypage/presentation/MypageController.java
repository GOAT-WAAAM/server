package com.goat.server.mypage.presentation;

import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.mypage.application.MypageService;
import com.goat.server.mypage.dto.request.GoalRequest;
import com.goat.server.mypage.dto.response.UserMajorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MypageController", description = "MypageController 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/goat/mypage")
public class MypageController {

    private final MypageService mypageService;
    @Operation(summary = "마이페이지 정보 보기", description = "마이페이지 정보 보기")
    @GetMapping("/info/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> getUserDetails(@PathVariable Long userId) {
        UserMajorResponse userMajorResponse = mypageService.getUserWithMajors(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(userMajorResponse));
    }

    @Operation(summary = "마이페이지 목표 수정하기", description = "마이페이지 목표 수정하기")
    @PatchMapping("/goal/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> updateGoal(@PathVariable Long userId, @RequestBody GoalRequest goalRequest) {

        mypageService.updateGoal(userId, goalRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(goalRequest));
    }
}
