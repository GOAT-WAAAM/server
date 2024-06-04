package com.goat.server.directory.presentation;

import com.goat.server.directory.dto.request.DirectoryInitRequest;
import com.goat.server.directory.dto.response.DirectoryTotalShowResponse;
import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.directory.application.DirectoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DirectoryController", description = "DirectoryController 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/goat/directory")
public class DirectoryController {

    private final DirectoryService directoryService;

    @Operation(summary = "과목, 폴더 정보 가져 오기", description = "과목, 폴더 정보 가져 오기")
    @GetMapping
    public ResponseEntity<ResponseTemplate<Object>> getDirectorySubList(
            @RequestParam(defaultValue = "0") Long directoryId,
            @AuthenticationPrincipal Long userId) {

        DirectoryTotalShowResponse directorySubList = directoryService.getDirectorySubList(userId, directoryId);

        log.info("directorySubList: {}", directorySubList);
        log.info("userId: {}", userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(directorySubList));
    }

    @Operation(summary = "폴더 생성", description = "폴더 생성")
    @PostMapping
    public ResponseEntity<ResponseTemplate<Object>> initDirectory(
            @AuthenticationPrincipal Long userId,
            @RequestBody DirectoryInitRequest directoryInitRequest) {

        directoryService.initDirectory(userId, directoryInitRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }

    @Operation(summary = "폴더 삭제", description = "폴더 삭제")
    @DeleteMapping("/temporal/{directoryId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteDirectoryTemporal(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long directoryId) {

        log.info("userId: {}", userId);

        directoryService.deleteDirectoryTemporal(userId, directoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
