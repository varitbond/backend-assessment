package com.varit.backend.assessment.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.varit.backend.assessment.validation.UpdateAndPatchGroup;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    @NotNull(groups = UpdateAndPatchGroup.class)
    private Integer id;
    @Schema(example = "1")
    @NotNull
    private Integer userId;
    @Schema(example = "Post title")
    @NotBlank
    private String title;
    @Schema(example = "Post body")
    @NotBlank
    private String body;
}
