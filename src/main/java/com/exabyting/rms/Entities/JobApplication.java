package com.exabyting.rms.Entities;

import com.exabyting.rms.Entities.Helper.JobApplicationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class JobApplication extends BaseEntity{

    private String socialUrl;
    private String resumeLink;
    private JobApplicationStatus status;
    @ManyToOne
    @JoinColumn(name = "job_opening_id")
    private JobOpening jobOpening;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
