package com.exabyting.rms.Entities;

import com.exabyting.rms.Entities.Helper.JobOpeningStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class JobOpening extends BaseEntity {

    @Column(nullable = false, length = 500)
    private String jobTitle;
    @Column(nullable = false, length = 1000)
    private String jobDescription;
    @Column(nullable = false, length = 1000)
    private String requirements;
    private Integer NoOfVacancy;
    @Column(nullable = false)
    private LocalDateTime deadLine;
    @Column(nullable = false)
    private JobOpeningStatus status;
    @OneToMany(mappedBy = "jobOpening",cascade = CascadeType.ALL)
    private List<JobApplication> applications;

}
