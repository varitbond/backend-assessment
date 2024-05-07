package com.varit.backend.assessment.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.varit.backend.assessment.validation.CreateGroup;
import com.varit.backend.assessment.validation.PatchGroup;
import com.varit.backend.assessment.validation.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    @NotNull(groups = {UpdateGroup.class, PatchGroup.class})
    private Integer id;
    @NotNull(groups = {CreateGroup.class, UpdateGroup.class})
    private Integer userId;
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String title;
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String body;
}
