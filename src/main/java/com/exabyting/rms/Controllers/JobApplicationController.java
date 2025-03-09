package com.exabyting.rms.Controllers;

import com.exabyting.rms.DTOs.JobApplicationDto;
import com.exabyting.rms.Entities.Helper.JobApplicationStatus;
import com.exabyting.rms.Exception.ApiException;
import com.exabyting.rms.Exception.CustomException;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.Services.ApplicationServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/applications")
public class JobApplicationController {


    @Autowired
    private ApplicationServices applicationServices;

    @PreAuthorize("hasAuthority('APPLICANT')")
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid JobApplicationDto jobApplicationDto) throws ResourceNotFound, CustomException {

        return new ResponseEntity<>(applicationServices.create(jobApplicationDto), HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping("jobs/{jobId}")
    public ResponseEntity<?> byJobOpening(
            @PathVariable Integer jobId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String status){

        JobApplicationStatus jobApplicationStatus=null;
        if(status != null){
            jobApplicationStatus=JobApplicationStatus.valueOf(status);
        }
        return new ResponseEntity<>(applicationServices.byJob(pageNumber,pageSize,sortBy,sortDir,jobId,jobApplicationStatus),HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> byUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String status){

        JobApplicationStatus jobApplicationStatus=null;
        if(status != null){
            jobApplicationStatus=JobApplicationStatus.valueOf(status);
        }
        return  new ResponseEntity<>(applicationServices.byUser(pageNumber,pageSize,sortBy,sortDir,userId,jobApplicationStatus),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PutMapping("/{applicationId}/updateStatus/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer applicationId,@PathVariable String status) throws ResourceNotFound {

        return new ResponseEntity<>(applicationServices.update(applicationId,
                JobApplicationStatus.valueOf(status)),HttpStatus.OK);
    }


}
