package com.exabyting.rms.DTOs;

import com.exabyting.rms.Entities.Helper.Address;
import com.exabyting.rms.Entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Data
public class ProfileDto {

    private Integer id;
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    private String contactNumber;
    private String profileImgUrl;
    private Address address;

}
