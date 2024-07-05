package com.goat.server.notification.exception;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PushPermissionDeniedException extends RuntimeException {
    private final ErrorCode errorCode;
}
