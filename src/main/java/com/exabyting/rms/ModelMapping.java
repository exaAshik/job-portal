package com.exabyting.rms;

import com.exabyting.rms.DTOs.JobOpeningDto;
import com.exabyting.rms.DTOs.ProfileDto;
import com.exabyting.rms.DTOs.UserDto;
import com.exabyting.rms.Entities.JobOpening;
import com.exabyting.rms.Entities.Profile;
import com.exabyting.rms.Entities.User;

public class ModelMapping {

    public static User userDtoToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles());
        user.setPassword(userDto.getPassword());
        if(userDto.getProfile() != null) {
            user.setProfile(profileDtoToProfile(userDto.getProfile()));
        }
        return user;

    }

    public static Profile profileDtoToProfile(ProfileDto profileDto){
        Profile profile = new Profile();
        profile.setAddress(profileDto.getAddress());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setProfileImgUrl(profileDto.getProfileImgUrl());
        profile.setContactNumber(profileDto.getContactNumber());

        return profile;
    }

    public static UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setPassword(user.getPassword());
        if(user.getProfile() != null){
            userDto.setProfile(profileToProfileDto(user.getProfile()));
        }
        return userDto;
    }

    public static ProfileDto profileToProfileDto(Profile profile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setContactNumber(profile.getContactNumber());
        profileDto.setProfileImgUrl(profile.getProfileImgUrl());
        profileDto.setAddress(profile.getAddress());
        return profileDto;
    }


    public static JobOpening jobOpeningDtoToJobOpening(JobOpeningDto jobOpeningDto) {
        JobOpening jobOpening = new JobOpening();
        jobOpening.setId(jobOpeningDto.getId());
        jobOpening.setJobTitle(jobOpeningDto.getJobTitle());
        jobOpening.setJobDescription(jobOpeningDto.getJobDescription());
        jobOpening.setRequirements(jobOpeningDto.getRequirements());
        jobOpening.setNoOfVacancy(jobOpeningDto.getNoOfVacancy());
        jobOpening.setDeadLine(jobOpeningDto.getDeadLine());
        jobOpening.setStatus(jobOpeningDto.getStatus());
        return jobOpening;
    }

    public static JobOpeningDto jobOpeningToJobOpeningDto(JobOpening jobOpening) {
        JobOpeningDto jobOpeningDto = new JobOpeningDto();
        jobOpeningDto.setId(jobOpening.getId());
        jobOpeningDto.setJobTitle(jobOpening.getJobTitle());
        jobOpeningDto.setJobDescription(jobOpening.getJobDescription());
        jobOpeningDto.setRequirements(jobOpening.getRequirements());
        jobOpeningDto.setNoOfVacancy(jobOpening.getNoOfVacancy());
        jobOpeningDto.setDeadLine(jobOpening.getDeadLine());
        jobOpeningDto.setStatus(jobOpening.getStatus());
        return jobOpeningDto;
    }

}
