package com.goat.server.notification.exception.errorcode;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {

        PUSH_DENIED(HttpStatus.FORBIDDEN, "User denied push notification"),
        ;

        private final HttpStatus httpStatus;
        private final String message;
}