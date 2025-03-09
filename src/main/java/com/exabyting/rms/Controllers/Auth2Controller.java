package com.exabyting.rms.Controllers;

import com.exabyting.rms.Config.JwtTokenProvider;
import com.exabyting.rms.Config.JwtTokenValidator;
import com.exabyting.rms.DTOs.UserDto;
import com.exabyting.rms.Entities.User;
import com.exabyting.rms.ModelMapping;
import com.exabyting.rms.Repositories.UserRepository;
import com.exabyting.rms.Utils.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Auth2Controller {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("api/v1/me")
    public ResponseEntity<?> auth2(@RequestHeader("Authorization") String token) {

        System.out.println(token);
        String emailFromToken = jwtTokenProvider.getEmailFromToken(token);
        token = token.substring(7);

        User byEmail = userRepository.findByEmail(emailFromToken);
        UserDto userDto = ModelMapping.userToUserDto(byEmail);

        System.out.println(token);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(token);
        loginResponse.setUserDto(userDto);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("oauth2/authorization/github")
    ResponseEntity<?>ggt(){
        return new ResponseEntity<>("hello ",HttpStatus.OK);
    }


}
