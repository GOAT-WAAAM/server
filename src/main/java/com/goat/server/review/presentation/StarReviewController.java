package com.goat.server.review.presentation;

import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.review.application.StarReviewService;
import com.goat.server.review.dto.response.StarReviewResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "StarReviewController", description = "StarReviewController 관련 API")
@Slf4j
@RestController
@RequestMapping("/goat/directory/favorites")
@RequiredArgsConstructor
public class StarReviewController {

    private final StarReviewService starReviewService;

    @Operation(summary = "즐겨찾기 추가 또는 제거하기", description = "즐겨찾기 추가 또는 제거하기")
    @PostMapping("/{reviewId}")
    public ResponseEntity<ResponseTemplate<Object>> manageFavorite(
            @PathVariable Long reviewId) {

        starReviewService.manageFavorite(reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "즐겨찾기 목록 보기", description = "즐겨찾기 목록 보기")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getMyFavoriteReviews(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page) {

        StarReviewResponseList response = starReviewService.getMyFavoriteReviews(userId, page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
