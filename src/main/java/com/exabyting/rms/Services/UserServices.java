package com.exabyting.rms.Services;

import com.exabyting.rms.DTOs.UserDto;
import com.exabyting.rms.Entities.Helper.Role;
import com.exabyting.rms.Entities.User;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.Utils.PageableResponse;

import java.util.List;

public interface UserServices {

    UserDto create(UserDto userDto);
    UserDto update(UserDto userDto);
    PageableResponse<UserDto> alluser(Integer pageNumber,Integer pageSize,String sortBy,String sortDir,Role role);

    UserDto byId(Integer id) throws ResourceNotFound;

    void deleteUser(Integer id) throws ResourceNotFound;

    PageableResponse<UserDto> byRole(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Role role);


}
