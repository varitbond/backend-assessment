package com.varit.backend.assessment.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStatus {
    private String description;
    private String errorMessage;

    public ResponseStatus(ResponseStatusEnum responseStatusEnum) {
        this.description = responseStatusEnum.getDescription();
    }

    public ResponseStatus(ResponseStatusEnum responseStatusEnum, String errorMessage) {
        this.description = responseStatusEnum.getDescription();
        this.errorMessage = errorMessage;
    }
}
