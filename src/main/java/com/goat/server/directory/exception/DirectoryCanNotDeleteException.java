package com.goat.server.directory.exception;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DirectoryCanNotDeleteException extends RuntimeException {
    private final ErrorCode errorCode;
}
