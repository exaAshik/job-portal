package com.exabyting.rms.Services.Implementation;

import com.exabyting.rms.DTOs.ProfileDto;
import com.exabyting.rms.DTOs.UserDto;
import com.exabyting.rms.Entities.Helper.Address;
import com.exabyting.rms.Entities.Helper.Role;
import com.exabyting.rms.Entities.Profile;
import com.exabyting.rms.Entities.User;
import com.exabyting.rms.Exception.ResourceNotFound;
import com.exabyting.rms.ModelMapping;
import com.exabyting.rms.Repositories.UserRepository;
import com.exabyting.rms.Services.UserServices;
import com.exabyting.rms.Utils.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServices {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto create(UserDto userDto) {

        User user = ModelMapping.userDtoToUser(userDto);
        User save = userRepository.save(user);
        save.setPassword(null);
        return ModelMapping.userToUserDto(save);

    }

    @Override
    public UserDto update(UserDto userDto) {

            User user = ModelMapping.userDtoToUser(userDto);
            User save = userRepository.save(user);

            ModelMapping.userToUserDto(save);
            return ModelMapping.userToUserDto(save);

    }


    @Override
    public PageableResponse<UserDto> alluser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir,Role role) {

        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> all = userRepository.findAllWithRoleFilter(role,pageable);
        List<UserDto> collect = all.stream().map(ModelMapping::userToUserDto).toList();
        return new PageableResponse<>(collect,pageNumber,pageSize,all.getTotalElements());
    }

    @Override
    public UserDto byId(Integer id) throws ResourceNotFound {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("user not found with id :" + id));
        return ModelMapping.userToUserDto(user);
    }

    @Override
    public void deleteUser(Integer id) throws ResourceNotFound {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFound("user not found with id :" + id));
        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> byRole(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Role role) {
        Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> usersByRole = userRepository.findByRolesContaining(role, pageable);
        List<UserDto> list = usersByRole.stream().map(ModelMapping::userToUserDto).toList();

        return new PageableResponse<>(list,pageNumber,pageSize,usersByRole.getTotalElements());
    }


}
