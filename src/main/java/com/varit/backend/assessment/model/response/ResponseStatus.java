package com.varit.backend.assessment.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseStatus {
    private String description;

    public ResponseStatus(ResponseStatusEnum responseStatusEnum) {
        this.description = responseStatusEnum.getDescription();
    }
}
