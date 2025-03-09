package com.exabyting.rms.Services;

import com.exabyting.rms.DTOs.JobOpeningDto;
import com.exabyting.rms.Entities.Helper.JobOpeningStatus;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.Utils.PageableResponse;

public interface JobOpeningServices {

    JobOpeningDto crate(JobOpeningDto jobOpeningDto);
    JobOpeningDto byId(Integer id) throws ResourceNotFound;

    PageableResponse<JobOpeningDto> alljob(Integer pageNumber, Integer pageSize, String sortBy, String sortDir,JobOpeningStatus status);

    JobOpeningDto update(Integer id,JobOpeningDto jobOpeningDto) throws ResourceNotFound;

    PageableResponse<JobOpeningDto> alljobByStatus(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, JobOpeningStatus status);

    void delete(Integer id) throws ResourceNotFound;
}
