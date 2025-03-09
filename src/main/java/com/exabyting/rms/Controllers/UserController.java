package com.exabyting.rms.Controllers;

import com.exabyting.rms.DTOs.ProfileUpdateDto;
import com.exabyting.rms.DTOs.UserDto;
import com.exabyting.rms.Entities.Helper.Role;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.Services.UserServices;
import com.exabyting.rms.Utils.PageableResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    @Autowired
    private UserServices userServices;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody @Valid UserDto user){

        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            UserDto createdUser = userServices.create(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User creation failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> allusers(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String role
    ){
        Role userRole = null;
        if(role != null){
            userRole = Role.valueOf(role);
        }
        PageableResponse<UserDto> alluser = userServices.alluser(pageNumber, pageSize, sortBy, sortDir,userRole);

        return new ResponseEntity<>(alluser,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable Integer id ) throws ResourceNotFound {
        return new ResponseEntity<>(userServices.byId(id),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles/{roleName}")
    public ResponseEntity<?> byRole(
            @PathVariable String roleName,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir                        ){

        try {
            Role role = Role.valueOf(roleName.toUpperCase());
            return new ResponseEntity<>(userServices.byRole(pageNumber,pageSize,sortBy,sortDir,role),HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role name");
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws ResourceNotFound {

        userServices.deleteUser(id);
        return new ResponseEntity<>("deleted Successfully ",HttpStatus.OK);
    }


    @PutMapping("/update/{userId}")
    public ResponseEntity<?>update(@PathVariable Integer userId,@RequestBody @Valid ProfileUpdateDto userDto){
        try {
            UserDto userDto1 = userServices.byId(userId);
            userDto1.setName(userDto.getName());
            userDto1.setRoles(userDto.getRoles());
            userDto1.setProfile(userDto.getProfile());
            userDto1.setPassword(userDto1.getPassword());
            UserDto update = userServices.update(userDto1);
            return new ResponseEntity<>(update,HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
                         