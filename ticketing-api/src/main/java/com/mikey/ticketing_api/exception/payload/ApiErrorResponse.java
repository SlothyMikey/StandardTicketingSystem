package com.mikey.ticketing_api.exception.payload;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String code,
        String message,
        String path,
        List<ApiFieldError> fieldErrors
) {
    public static ApiErrorResponse of(
            OffsetDateTime timestamp,
            int status,
            String error,
            String code,
            String message,
            String path
    ) {
        return new ApiErrorResponse(timestamp, status, error, code, message, path, null);
    }

    public static ApiErrorResponse of(
            OffsetDateTime timestamp,
            int status,
            String error,
            String code,
            String message,
            String path,
            List<ApiFieldError> fieldErrors
    ) {
        return new ApiErrorResponse(timestamp, status, error, code, message, path, fieldErrors);
    }
}
