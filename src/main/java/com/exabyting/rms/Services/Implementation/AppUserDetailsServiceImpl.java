package com.exabyting.rms.Services.Implementation;

import com.exabyting.rms.Entities.Helper.Role;
import com.exabyting.rms.Entities.User;
import com.exabyting.rms.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user == null){
            throw  new UsernameNotFoundException("user not found with email "+ username);

        }
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());


        return  new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                authorities);

    }
}
