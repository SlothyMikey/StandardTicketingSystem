package com.mikey.ticketing_api.exception;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import com.mikey.ticketing_api.exception.payload.ApiErrorResponse;
import com.mikey.ticketing_api.exception.payload.ApiFieldError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<ApiFieldError> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toApiFieldError)
                .toList();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_FAILED,
                "Request validation failed",
                request,
                fieldErrors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableMessage(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.REQUEST_BODY_INVALID,
                "Request body is missing or malformed",
                request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        String parameterName = exception.getName();
        String message = "Invalid value for parameter '%s'".formatted(parameterName);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.METHOD_ARGUMENT_INVALID,
                message,
                request
        );
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(
            ApiException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                exception.getStatus(),
                exception.getErrorCode(),
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
            ResponseStatusException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());
        String reason = exception.getReason() != null ? exception.getReason() : "Request failed";

        return buildResponse(
                status,
                status == HttpStatus.NOT_FOUND ? ApiErrorCode.RESOURCE_NOT_FOUND : ApiErrorCode.ILLEGAL_STATE,
                reason,
                request
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalState(
            IllegalStateException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                ApiErrorCode.ILLEGAL_STATE,
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnhandledException(
            Exception exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ApiErrorCode.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                request
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            ApiErrorCode errorCode,
            String message,
            HttpServletRequest request
    ) {
        ApiErrorResponse body = ApiErrorResponse.of(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                errorCode.name(),
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            ApiErrorCode errorCode,
            String message,
            HttpServletRequest request,
            List<ApiFieldError> fieldErrors
    ) {
        ApiErrorResponse body = ApiErrorResponse.of(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                errorCode.name(),
                message,
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(status).body(body);
    }

    private ApiFieldError toApiFieldError(FieldError fieldError) {
        return new ApiFieldError(
                fieldError.getField(),
                normalizeRejectedValue(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage()
        );
    }

    private Object normalizeRejectedValue(Object rejectedValue) {
        if (rejectedValue == null) {
            return null;
        }
        if (rejectedValue instanceof CharSequence sequence && sequence.length() == 0) {
            return "";
        }
        return Objects.toString(rejectedValue);
    }
}
