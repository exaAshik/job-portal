package com.exabyting.rms.Controllers;

import com.exabyting.rms.Exception.ApiException;
import com.exabyting.rms.Services.CloudinaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    @Autowired
    private CloudinaryServices cloudinaryServices;


    @PostMapping("/image/upload/")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file){

        if(!isImage(file)) return new ResponseEntity<>(new ApiException("Please upload an image file",HttpStatus.EXPECTATION_FAILED),HttpStatus.EXPECTATION_FAILED);
        Map data = cloudinaryServices.upload(file);
        return  new ResponseEntity<>(data, HttpStatus.OK);

    }


    @PostMapping("/cv/upload/")
    public ResponseEntity<?> cvImage(@RequestParam("cv") MultipartFile file){

        if(!isPdf(file)) return new ResponseEntity<>(new ApiException("Please upload a pdf file",HttpStatus.EXPECTATION_FAILED),HttpStatus.EXPECTATION_FAILED);
        Map data = cloudinaryServices.upload(file);
        return  new ResponseEntity<>(data, HttpStatus.OK);

    }



    public static boolean isImage(MultipartFile file){

        String contentType = file.getContentType();
        if(contentType != null){
            return contentType.startsWith("image/");
        }
        return false;

    }

    public static boolean isPdf(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            return contentType.equals("application/pdf");
        }
        return false;
    }

}
