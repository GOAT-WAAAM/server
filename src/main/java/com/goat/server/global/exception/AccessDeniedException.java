package com.goat.server.global.exception;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessDeniedException extends RuntimeException{
    private final ErrorCode errorCode;
}
