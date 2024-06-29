package com.goat.server.review.exception;

import com.goat.server.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
}
