package com.goat.server.global.exception.handler;

import com.goat.server.directory.exception.DirectoryCanNotDeleteException;
import com.goat.server.directory.exception.DirectoryNotAuthException;
import com.goat.server.directory.exception.DirectoryNotFoundException;
import com.goat.server.mypage.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

import com.goat.server.global.exception.CustomFeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.goat.server.global.exception.errorcode.ErrorCode;
import com.goat.server.global.exception.errorcode.GlobalErrorCode;
import com.goat.server.global.exception.response.ErrorResponse;
import com.goat.server.global.exception.response.ErrorResponse.ValidationError;
import com.goat.server.global.exception.response.ErrorResponse.ValidationErrors;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger("ErrorLogger");
    private static final String LOG_FORMAT_INFO = "\n[🔵INFO] - ({} {})\n(id: {}, role: {})\n{}\n {}: {}";
    private static final String LOG_FORMAT_WARN = "\n[🟠WARN] - ({} {})\n(id: {}, role: {})";
    private static final String LOG_FORMAT_ERROR = "\n[🔴ERROR] - ({} {})\n(id: {}, role: {})";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        logInfo(GlobalErrorCode.INVALID_PARAMETER, e, request);
        ErrorCode errorCode = GlobalErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(DirectoryNotFoundException.class)
    public ResponseEntity<Object> handleDirectoryNotFound(DirectoryNotFoundException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(DirectoryCanNotDeleteException.class)
    public ResponseEntity<Object> handleDirectoryCanNotDelete(DirectoryCanNotDeleteException e,
                                                              HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(DirectoryNotAuthException.class)
    public ResponseEntity<Object> handleDirectoryNotAuth(DirectoryNotAuthException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    /**
     * Feign 관련 exception 처리
     */
    @ExceptionHandler(CustomFeignException.class)
    public ErrorResponse handleTokenNotExist(CustomFeignException e, HttpServletRequest request) {
        logInfo(e.getErrorCode(), e, request);
        ErrorCode errorCode = e.getErrorCode();
        return makeErrorResponse(errorCode, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e, HttpServletRequest request) {
        logError(e, request);
        ErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    /**
     * DTO @Valid 관련 exception 처리
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorCode errorCode = GlobalErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(e, errorCode);
    }

    /**
     * HTTP Method 관련 exception 처리
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorCode errorCode = GlobalErrorCode.INVALID_METHOD;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .isSuccess(false)
                .code(errorCode.name())
                .message(message)
                .results(new ValidationErrors(null))
                .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
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

    private void logInfo(ErrorCode ec, Exception e, HttpServletRequest request) {
        log.info(LOG_FORMAT_INFO, request.getMethod(), request.getRequestURI(), getUserId(),
                getRole(), ec.getHttpStatus(), e.getClass().getName(), e.getMessage());
    }

    private void logWarn(Exception e, HttpServletRequest request) {
        log.warn(LOG_FORMAT_WARN, request.getMethod(), request.getRequestURI(), getUserId(), getRole(), e);
    }

    private void logError(Exception e, HttpServletRequest request) {
        log.error(LOG_FORMAT_ERROR, request.getMethod(), request.getRequestURI(), getUserId(), getRole(), e);
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // 사용자의 id
        } else {
            return "anonymous";
        }
    }

    private String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().toString(); // 사용자의 role
        } else {
            return "anonymous";
        }
    }
}
