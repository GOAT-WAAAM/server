package com.goat.server.review.presentation;

import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.review.application.ReviewService;
import com.goat.server.review.dto.request.ReviewMoveRequest;
import com.goat.server.review.dto.request.ReviewUpdateRequest;
import com.goat.server.review.dto.request.ReviewUploadRequest;
import com.goat.server.review.dto.response.ReviewHomeResponseList;
import com.goat.server.review.dto.response.ReviewDetailResponse;
import com.goat.server.review.dto.response.RandomReviewsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ReviewController", description = "ReviewController 관련 API")
@Slf4j
@RestController
@RequestMapping("/goat")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "홈 화면", description = "홈 화면 최근 복습 이미지")
    @GetMapping("/home")
    public ResponseEntity<ResponseTemplate<Object>> getRecentReviews(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "0") int page) {

        ReviewHomeResponseList response = reviewService.getRecentReviews(userId, page);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "복습 등록하기", description = "복습 등록하기")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},value ="/directory/review")
    public ResponseEntity<ResponseTemplate<Object>> uploadReview(
            @AuthenticationPrincipal Long userId,
            @RequestPart(required = false) MultipartFile multipartFile,
            @RequestPart ReviewUploadRequest request) {

        log.info("[ReviewController.uploadReview]");

        reviewService.uploadReview(userId, multipartFile, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(request));
    }

    @Operation(summary = "복습 자료 상세 보기", description = "복습 자료 상세 보기")
    @GetMapping ("/directory/{reviewId}")
    public ResponseEntity<ResponseTemplate<Object>> getReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long reviewId) {

        ReviewDetailResponse response = reviewService.getDetailReview(userId, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "복습 수정하기", description = "복습 수정하기")
    @PutMapping (consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}, value ="/directory/{reviewId}")
    public ResponseEntity<ResponseTemplate<Object>> updateReview(
            @PathVariable Long reviewId,
            @RequestPart(required = false) MultipartFile multipartFile,
            @RequestPart ReviewUpdateRequest request) {

        reviewService.updateReview(reviewId, multipartFile, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "복습 삭제하기", description = "복습 삭제하기")
    @DeleteMapping ("/directory/{reviewId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long reviewId) {

        reviewService.deleteReview(userId, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "복습 자료를 창고로 보내기", description = "복습 자료를 복습창고로 이동")
    @PatchMapping("/directory/storage/{reviewId}")
    public ResponseEntity<ResponseTemplate<Object>> moveReviewToStorage(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long reviewId) {

        reviewService.moveReviewToStorage(userId, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "복습 자료를 휴지통으로 보내기", description = "복습 자료를 휴지통으로 이동")
    @PatchMapping("/directory/trashcan/{reviewId}")
    public ResponseEntity<ResponseTemplate<Object>> moveReviewToTrashCan(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long reviewId) {

        reviewService.moveReviewToTrashCan(userId, reviewId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "복습 자료를 다른 폴더로 이동", description = "복습 자료를 다른 폴더로 이동")
    @PatchMapping("/directory/review/move")
    public ResponseEntity<ResponseTemplate<Object>> moveReviewDirectory(
            @RequestBody ReviewMoveRequest request) {

        reviewService.moveReviewDirectory(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "복습 정보 나중에 입력하기", description = "복습 정보 나중에 입력하기")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},value ="/directory/review/storage")
    public ResponseEntity<ResponseTemplate<Object>> uploadReviewLater(
            @AuthenticationPrincipal Long userId,
            @RequestPart MultipartFile multipartFile) {

        reviewService.uploadReviewLater(userId, multipartFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "바로 복습 누르기", description = "바로 복습 누르기")
    @GetMapping("/loading/random-reviews")
    public ResponseEntity<ResponseTemplate<Object>> loadRandomReviews(
            @AuthenticationPrincipal Long userId) {

        reviewService.loadRandomReviews(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "바로 복습 들어가서 랜덤 복습자료 보여주기", description = "바로 복습 들어가서 랜덤 복습자료 하나씩 보여주기")
    @GetMapping("/random-review")
    public ResponseEntity<ResponseTemplate<Object>> getRandomReview(
            @AuthenticationPrincipal Long userId) {

        RandomReviewsResponse response = reviewService.getRandomReview(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "놓친 복습 조회", description = "확인하지 않은 push 알림의 복습 조회")
    @GetMapping("/missed-review")
    public ResponseEntity<ResponseTemplate<Object>> getMissedReview(
            @AuthenticationPrincipal Long userId) {

        MissedReviewResponse response = reviewService.getMissedReview(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}
