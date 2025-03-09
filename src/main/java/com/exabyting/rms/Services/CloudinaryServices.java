package com.exabyting.rms.Services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryServices {

    public Map upload(MultipartFile file);
}
