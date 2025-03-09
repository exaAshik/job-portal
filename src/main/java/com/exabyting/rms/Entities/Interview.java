package com.exabyting.rms.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Interview extends BaseEntity{

    private LocalDateTime schedule;
    private String link;
    private String reportLink;

//    @ManyToMany
//    @JoinTable(name = "interviewer" ,joinColumns = {@JoinColumn(name = "interview_id")}
//    ,inverseJoinColumns = {@JoinColumn(name = "user_id")})
//    private List<User> interviewers;
//
//    @ManyToOne
//    @JoinColumn(name = "candidate_id")
//    private User candidate;
//
//    @ManyToOne
//    @JoinColumn(name = "job_application_id")
//    private JobApplication jobApplication;
//

}
