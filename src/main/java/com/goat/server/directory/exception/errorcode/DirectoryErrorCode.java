package com.goat.server.directory.exception.errorcode;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DirectoryErrorCode implements ErrorCode {

    DIRECTORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Directory not found."),
    DIRECTORY_CANNOT_DELETE(HttpStatus.FORBIDDEN, "Directory cannot delete."),
    DIRECTORY_NOT_AUTH(HttpStatus.FORBIDDEN, "해당 폴더에 대한 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
