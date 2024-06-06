package com.goat.server.directory.exception.errorcode;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DirectoryErrorCode implements ErrorCode {

    DIRECTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Directory not found."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
