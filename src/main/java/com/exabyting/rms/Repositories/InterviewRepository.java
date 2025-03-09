package com.exabyting.rms.Repositories;

import com.exabyting.rms.Entities.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview,Integer> {
}
