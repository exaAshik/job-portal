package com.exabyting.rms.DTOs;

import com.exabyting.rms.Entities.Helper.JobOpeningStatus;
import com.exabyting.rms.Entities.JobApplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobOpeningDto {

    private Integer id;
    @NotBlank(message = "jobTitle is mandatory")
    private String jobTitle;

    @NotBlank(message = "jobDescription is mandatory")
    private String jobDescription;

    @NotBlank(message = "requirements is mandatory")
    private String requirements;

    @NotNull(message = "noOfVacancy is mandatory")
    private Integer noOfVacancy;

    @NotNull(message = "deadLine is mandatory")
    private LocalDateTime deadLine;

    private JobOpeningStatus status;

}
