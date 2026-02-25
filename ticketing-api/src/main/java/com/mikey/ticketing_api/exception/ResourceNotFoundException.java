package com.mikey.ticketing_api.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(
                HttpStatus.NOT_FOUND,
                ApiErrorCode.RESOURCE_NOT_FOUND,
                String.format("%s with id '%s' was not found", resourceName, resourceId)
        );
    }

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ApiErrorCode.RESOURCE_NOT_FOUND, message);
    }
}
