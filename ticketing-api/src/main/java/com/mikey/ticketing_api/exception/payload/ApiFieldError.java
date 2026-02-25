package com.mikey.ticketing_api.exception.payload;

public record ApiFieldError(
        String field,
        Object rejectedValue,
        String message
) {
}
