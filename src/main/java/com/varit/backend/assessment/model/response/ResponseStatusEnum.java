package com.varit.backend.assessment.model.response;

import lombok.Getter;

@Getter
public enum ResponseStatusEnum {
    SUCCESS("Success"),
    AUTHENTICATION_ERROR("Authentication Error");
    private final String description;

    ResponseStatusEnum(String description) {
        this.description = description;
    }
}
