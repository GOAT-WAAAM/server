package com.goat.server.directory.presentation;

import com.goat.server.directory.dto.response.DirectoryResponseList;
import com.goat.server.global.dto.ResponseTemplate;
import com.goat.server.directory.application.DirectoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DirectoryController", description = "DirectoryController 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/directory")
public class DirectoryController {

    private final DirectoryService directoryService;

    @Operation(summary = "과목, 폴더 정보 가져 오기", description = "과목, 폴더 정보 가져 오기")
    @GetMapping("/{directoryId}/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> getDirectoryList(
            @PathVariable Long directoryId,
            @PathVariable Long userId) {

        DirectoryResponseList directoryList = directoryService.getDirectoryList(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(directoryList));
    }

    @Operation(summary = "폴더 삭제", description = "폴더 삭제")
    @DeleteMapping("/delete-directory/{directoryId}/{userId}")
    public ResponseEntity<ResponseTemplate<Object>> deleteDirectory(
            @PathVariable Long directoryId,
            @PathVariable Long userId) {

        directoryService.deleteDirectory(userId, directoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
