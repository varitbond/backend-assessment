package com.varit.backend.assessment.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.varit.backend.assessment.validation.UpdateAndPatchGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @NotNull(groups = UpdateAndPatchGroup.class)
    private Integer id;
    @NotNull
    private Integer userId;
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
