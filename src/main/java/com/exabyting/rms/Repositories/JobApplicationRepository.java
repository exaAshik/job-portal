package com.exabyting.rms.Repositories;

import com.exabyting.rms.Entities.Helper.JobApplicationStatus;
import com.exabyting.rms.Entities.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Integer> {

    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId AND (:status IS NULL OR ja.status = :status)")
    Page<JobApplication> findByUserId(
            @Param("userId") Integer userId,
            @Param("status") JobApplicationStatus status,
            Pageable pageable
    );
    @Query("SELECT ja FROM JobApplication ja WHERE ja.jobOpening.id = :jobOpeningId AND (:status IS NULL OR ja.status = :status)")
    Page<JobApplication> findByJobOpeningId(
            @Param("jobOpeningId") Integer jobOpeningId,
            @Param("status") JobApplicationStatus status,
            Pageable pageable
    );

    List<JobApplication> findByUserIdAndJobOpeningId(Integer userId,Integer jobOpeningId);

}
