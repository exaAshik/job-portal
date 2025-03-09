package com.exabyting.rms.Controllers;

import com.exabyting.rms.Config.JwtTokenProvider;
import com.exabyting.rms.Config.JwtTokenValidator;
import com.exabyting.rms.DTOs.UserDto;
import com.exabyting.rms.Entities.User;
import com.exabyting.rms.ModelMapping;
import com.exabyting.rms.Repositories.UserRepository;
import com.exabyting.rms.Utils.LoginRequest;
import com.exabyting.rms.Utils.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    @Autowired
    private UserRepository userRepository;



    public Authentication authentication(String email, String password){

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if(userDetails == null){
            throw  new BadCredentialsException("Invalid userName ...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    }

    @GetMapping("/github")
    public ResponseEntity<?>OAuth2(OAuth2UserRequest oAuth2UserRequest){

        return new ResponseEntity<>("hello",HttpStatus.OK);
    }




    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest){

        String email = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Authentication authenticationuser = authentication(email,password);
        SecurityContextHolder.getContext().setAuthentication(authenticationuser);
        String jwtToken = jwtTokenProvider.generateToken(authenticationuser);

        String name = authenticationuser.getName();
        User loggedUser = userRepository.findByEmail(name);
        UserDto userDto = ModelMapping.userToUserDto(loggedUser);
        userDto.setPassword(null);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(jwtToken);
        loginResponse.setUserDto(userDto);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);


    }
}
