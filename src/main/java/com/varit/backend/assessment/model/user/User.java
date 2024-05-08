package com.varit.backend.assessment.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.varit.backend.assessment.model.user.address.Address;
import com.varit.backend.assessment.model.user.company.Company;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @NotNull(groups = UpdateAndPatchGroup.class)
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
}
