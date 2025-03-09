package com.exabyting.rms.Repositories;

import com.exabyting.rms.Entities.Helper.JobOpeningStatus;
import com.exabyting.rms.Entities.JobOpening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobOpeningRepository extends JpaRepository<JobOpening,Integer> {

    Page<JobOpening> findByStatus(JobOpeningStatus status, Pageable pageable);

    @Query("SELECT jo FROM JobOpening jo WHERE (:status IS NULL OR jo.status = :status)")
    Page<JobOpening> findAllJobByStatus(@Param("status") JobOpeningStatus status, Pageable pageable);
}
