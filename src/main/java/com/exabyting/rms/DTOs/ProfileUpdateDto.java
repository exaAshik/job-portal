package com.exabyting.rms.DTOs;

import com.exabyting.rms.Entities.Helper.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileUpdateDto {

    private Integer id;


    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Roles cannot be null")
    private List<Role> roles=new ArrayList<>();

    @NotNull(message = "Profile is mandatory")
    private ProfileDto profile;
}
