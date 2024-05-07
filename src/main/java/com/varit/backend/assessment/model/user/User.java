package com.varit.backend.assessment.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.varit.backend.assessment.model.user.address.Address;
import com.varit.backend.assessment.model.user.company.Company;
import com.varit.backend.assessment.validation.CreateGroup;
import com.varit.backend.assessment.validation.PatchGroup;
import com.varit.backend.assessment.validation.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @NotNull(groups = {UpdateGroup.class, PatchGroup.class})
    private Integer id;
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String name;
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String username;
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
}
