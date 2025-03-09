package com.exabyting.rms.Controllers;

import com.exabyting.rms.DTOs.JobOpeningDto;
import com.exabyting.rms.Entities.Helper.JobOpeningStatus;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.Services.JobOpeningServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/jobs")
public class JobOpeningControllers {


    @Autowired
    private JobOpeningServices jobOpeningServices;


    @PreAuthorize("hasAnyAuthority('HR', 'ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid JobOpeningDto jobOpening){

        return new ResponseEntity<>(jobOpeningServices.crate(jobOpening), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable Integer id) throws ResourceNotFound {
        return new ResponseEntity<>(jobOpeningServices.byId(id),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> alljob(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String status
    ){
        JobOpeningStatus jobOpeningStatus=null;
        if(status != null){
            jobOpeningStatus =  JobOpeningStatus.valueOf(status.toUpperCase());
        }
        return new ResponseEntity<>(jobOpeningServices.alljob(pageNumber,pageSize,sortBy,sortDir,jobOpeningStatus),HttpStatus.OK);
    }

    @GetMapping("status/{status}")
    public ResponseEntity<?> alljobByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        JobOpeningStatus jobOpeningStatus = JobOpeningStatus.valueOf(status.toUpperCase());
        return new ResponseEntity<>(jobOpeningServices.alljobByStatus(pageNumber,pageSize,sortBy,sortDir,jobOpeningStatus),HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('HR', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody JobOpeningDto jobOpeningDto,
                                    @PathVariable Integer id) throws ResourceNotFound {
        return new ResponseEntity<>(jobOpeningServices.update(id,jobOpeningDto),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('HR', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFound {
        jobOpeningServices.delete(id);
        return  new ResponseEntity<>("deleted successfully",HttpStatus.OK);
    }
}
