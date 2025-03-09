package com.exabyting.rms.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<?>hello(){
        return new ResponseEntity<>("hello from ngrok", HttpStatus.OK);
    }
}
