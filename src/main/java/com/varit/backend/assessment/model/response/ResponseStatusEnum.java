package com.varit.backend.assessment.model.response;

import lombok.Getter;

@Getter
public enum ResponseStatusEnum {
    SUCCESS("Success"),
    AUTHENTICATION_ERROR("Authentication Error"),
    NOT_FOUND("Not found"),
    BUSINESS_ERROR("Business error"),
    INPUT_VALIDATION_ERROR("Input validation error"),
    INTERNAL_ERROR("Internal error");
    private final String description;

    ResponseStatusEnum(String description) {
        this.description = description;
    }
}
