package com.exabyting.rms.Services.Implementation;

import com.exabyting.rms.Config.JwtTokenProvider;
import com.exabyting.rms.DTOs.UserDto;
import com.exabyting.rms.Entities.Helper.Role;
import com.exabyting.rms.Entities.User;
import com.exabyting.rms.ModelMapping;
import com.exabyting.rms.Repositories.UserRepository;
import com.exabyting.rms.Services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class Auth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        String login = (String) attributes.get("login");
        String name = (String) attributes.get("name");
        String email =(String)attributes.get("email");

        if(login == null) login=email;

        System.out.println(attributes);



        User byEmail = userRepository.findByEmail(login);

        if(byEmail == null){
            UserDto oauth2user = UserDto.builder()
                    .name(name)
                    .email(login)
                    .password(passwordEncoder.encode("oauth2"))
                    .roles(List.of(Role.APPLICANT))
                    .profile(null)
                    .build();

            userServices.create(oauth2user);

        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String s = jwtTokenProvider.generateToken(usernamePasswordAuthenticationToken);

        System.out.println(attributes);
        System.out.println(s);
        response.sendRedirect("http://172.16.20.35:5173/sucess?token=" +s);



    }
}
