package com.goat.server.auth.exception;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
