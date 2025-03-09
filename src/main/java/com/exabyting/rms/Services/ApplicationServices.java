package com.exabyting.rms.Services;

import com.exabyting.rms.DTOs.JobApplicationDto;
import com.exabyting.rms.DTOs.JobOpeningDto;
import com.exabyting.rms.Entities.Helper.JobApplicationStatus;
import com.exabyting.rms.Exception.ApiException;
import com.exabyting.rms.Exception.CustomException;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.Utils.PageableResponse;

public interface ApplicationServices {

    JobApplicationDto create (JobApplicationDto jobApplicationDto) throws ResourceNotFound, CustomException;
    JobApplicationDto update(Integer id, JobApplicationStatus status) throws ResourceNotFound;

    PageableResponse<JobApplicationDto> byJob(Integer pageNumber, Integer pageSize, String sortBy, String sortDir,Integer jobId,JobApplicationStatus status);
    PageableResponse<JobApplicationDto> byUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir,Integer userId,JobApplicationStatus status);



}
