package com.goat.server.review.exception.errorcode;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode{

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "Review not found");

    private final HttpStatus httpStatus;
    private final String message;
}