package com.exabyting.rms.Services.Implementation;

import com.exabyting.rms.DTOs.JobOpeningDto;
import com.exabyting.rms.Entities.Helper.JobOpeningStatus;
import com.exabyting.rms.Entities.JobOpening;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.ModelMapping;
import com.exabyting.rms.Repositories.JobOpeningRepository;
import com.exabyting.rms.Services.JobOpeningServices;
import com.exabyting.rms.Utils.PageableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOpeningServiceImpl implements JobOpeningServices {

    @Autowired
    private JobOpeningRepository jobOpeningRepository;

    @Override
    public JobOpeningDto crate(JobOpeningDto jobOpeningDto) {

        JobOpening jobOpening = ModelMapping.jobOpeningDtoToJobOpening(jobOpeningDto);
        JobOpening save = jobOpeningRepository.save(jobOpening);
        return ModelMapping.jobOpeningToJobOpeningDto(save);

    }

    @Override
    public JobOpeningDto byId(Integer id) throws ResourceNotFound {
        return ModelMapping.jobOpeningToJobOpeningDto(
                jobOpeningRepository.findById(id).orElseThrow(()->new ResourceNotFound("Job not found with id "+id))
        );
    }

    @Override
    public PageableResponse<JobOpeningDto> alljob(Integer pageNumber, Integer pageSize, String sortBy, String sortDir,JobOpeningStatus status) {
        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<JobOpening> alljobs = jobOpeningRepository.findAllJobByStatus(status,pageable);
        List<JobOpeningDto> list =
                alljobs.stream().map(ModelMapping::jobOpeningToJobOpeningDto).toList();
        return new PageableResponse<>(list,pageNumber,pageSize,alljobs.getTotalElements());
    }

    @Override
    public JobOpeningDto update(Integer id, JobOpeningDto jobOpeningDto) throws ResourceNotFound {

        JobOpening jobOpening = jobOpeningRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Job not found with id " + id));
        JobOpening jobOpening1 = ModelMapping.jobOpeningDtoToJobOpening(jobOpeningDto);
        jobOpening1.setId(jobOpening.getId());
        return ModelMapping.jobOpeningToJobOpeningDto(
                jobOpeningRepository.save(jobOpening1)
        );
    }

    @Override
    public PageableResponse<JobOpeningDto> alljobByStatus(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, JobOpeningStatus status) {

        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<JobOpening> alljobs = jobOpeningRepository.findByStatus(status, pageable);
        List<JobOpeningDto> list =
                alljobs.stream().map(ModelMapping::jobOpeningToJobOpeningDto).toList();
        return new PageableResponse<>(list,pageNumber,pageSize,alljobs.getTotalElements());
    }

    @Override
    public void delete(Integer id) throws ResourceNotFound {
        JobOpening jobOpening = jobOpeningRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Job not found with id " + id));
        jobOpeningRepository.delete(jobOpening);
    }


}
