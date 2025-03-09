package com.exabyting.rms.Utils;

import com.exabyting.rms.DTOs.UserDto;
import lombok.Data;

@Data
public class LoginResponse {

    private String jwtToken;
    private UserDto userDto;
}
