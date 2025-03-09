package com.exabyting.rms.Services.Implementation;

import com.exabyting.rms.DTOs.JobApplicationDto;
import com.exabyting.rms.DTOs.JobOpeningDto;
import com.exabyting.rms.Entities.Helper.JobApplicationStatus;
import com.exabyting.rms.Entities.JobApplication;
import com.exabyting.rms.Entities.JobOpening;
import com.exabyting.rms.Entities.User;
import com.exabyting.rms.Exception.ApiException;
import com.exabyting.rms.Exception.CustomException;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.Repositories.JobApplicationRepository;
import com.exabyting.rms.Repositories.JobOpeningRepository;
import com.exabyting.rms.Repositories.UserRepository;
import com.exabyting.rms.Services.ApplicationServices;
import com.exabyting.rms.Utils.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements ApplicationServices {


    @Autowired
    private JobOpeningRepository jobOpeningRepository;
    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public JobApplicationDto create(JobApplicationDto jobApplicationDto) throws ResourceNotFound, CustomException {


        User user = userRepository.findById(jobApplicationDto.getUserId()).orElseThrow(
                () -> new ResourceNotFound("userNotFound with id " + jobApplicationDto.getUserId())
        );

        JobOpening jobOpening = jobOpeningRepository.findById(jobApplicationDto.getJobOpeningId()).orElseThrow(
                ()->new ResourceNotFound("Job not found")
        );

        List<JobApplication> applications = applicationRepository.findByUserIdAndJobOpeningId(jobApplicationDto.getUserId(),jobApplicationDto.getJobOpeningId());
        if(!applications.isEmpty()) throw new CustomException("cant apply more than one time", HttpStatus.BAD_REQUEST);


        JobApplication application = JobApplication.builder()
                .resumeLink(jobApplicationDto.getResumeLink())
                .socialUrl(jobApplicationDto.getSocialUrl())
                .jobOpening(jobOpening)
                .user(user)
                .status(JobApplicationStatus.PENDING)
                .build();
        JobApplication save = applicationRepository.save(application);


        return JobApplicationDto.builder()
                .id(save.getId())
                .socialUrl(save.getSocialUrl())
                .jobOpeningId(jobApplicationDto.getJobOpeningId())
                .userId(jobApplicationDto.getUserId())
                .resumeLink(save.getResumeLink())
                .status(save.getStatus())
                .build();
    }

    @Override
    public JobApplicationDto update(Integer id, JobApplicationStatus status) throws ResourceNotFound {
        JobApplication application = applicationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound("application not found"));
        application.setStatus(status);
        JobApplication save = applicationRepository.save(application);
        return JobApplicationDto.builder()
                .id(save.getId())
                .jobOpeningId(save.getJobOpening().getId())
                .userId(save.getUser().getId())
                .resumeLink(save.getResumeLink())
                .status(save.getStatus())
                .build();

    }

    @Override
    public PageableResponse<JobApplicationDto> byJob(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Integer jobId,JobApplicationStatus status) {

        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<JobApplication> byJobOpeningId = applicationRepository.findByJobOpeningId(jobId,status, pageable);
        List<JobApplicationDto> list = byJobOpeningId.stream().map(e -> JobApplicationDto.builder()
                .jobOpeningId(e.getId())
                .id(e.getId())
                .userId(e.getUser().getId())
                .resumeLink(e.getResumeLink())
                .status(e.getStatus())
                .build()).toList();

        return new PageableResponse<>(list,pageNumber,pageSize,byJobOpeningId.getTotalElements());
    }

    @Override
    public PageableResponse<JobApplicationDto> byUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Integer userId,JobApplicationStatus status) {
        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<JobApplication> byJobOpeningId = applicationRepository.findByUserId(userId,status, pageable);
        List<JobApplicationDto> list = byJobOpeningId.stream().map(e -> JobApplicationDto.builder()
                .jobOpeningId(e.getJobOpening().getId())
                .id(e.getId())
                .userId(e.getUser().getId())
                .resumeLink(e.getResumeLink())
                .status(e.getStatus())
                .userId(e.getUser().getId())
                .socialUrl(e.getSocialUrl())
                .build()).toList();

        return new PageableResponse<>(list,pageNumber,pageSize,byJobOpeningId.getTotalElements());
    }
}
