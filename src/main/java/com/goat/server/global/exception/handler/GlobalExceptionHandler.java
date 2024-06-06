package com.goat.server.global.exception.handler;

import com.goat.server.directory.exception.DirectoryNotFoundException;
import com.goat.server.mypage.exception.UserNotFoundException;
import java.util.List;

import com.goat.server.global.exception.CustomFeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.goat.server.global.exception.errorcode.ErrorCode;
import com.goat.server.global.exception.errorcode.GlobalErrorCode;
import com.goat.server.global.exception.response.ErrorResponse;
import com.goat.server.global.exception.response.ErrorResponse.ValidationError;
import com.goat.server.global.exception.response.ErrorResponse.ValidationErrors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        final ErrorCode errorCode = GlobalErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(final UserNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(DirectoryNotFoundException.class)
    public ResponseEntity<Object> handleDirectoryNotFound(final DirectoryNotFoundException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    /**
     * Feign 관련 exception 처리
     */
    @ExceptionHandler(CustomFeignException.class)
    public ErrorResponse handleTokenNotExist(final CustomFeignException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return makeErrorResponse(errorCode, e.getMessage());
    }


    /**
     * DTO @Valid 관련 exception 처리
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {
        log.warn("handleIllegalArgument", e);
        final ErrorCode errorCode = GlobalErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(final Exception ex) {
        log.warn("handleAllException", ex);
        final ErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode, final String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(message)
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(final BindException e, final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(final BindException e, final ErrorCode errorCode) {
        final List<ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::from)
                .toList();

        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(validationErrorList))
                .build();
    }
}
