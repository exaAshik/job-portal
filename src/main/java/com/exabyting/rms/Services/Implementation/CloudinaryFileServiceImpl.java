package com.exabyting.rms.Services.Implementation;

import com.cloudinary.Cloudinary;
import com.exabyting.rms.Services.CloudinaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryFileServiceImpl implements CloudinaryServices {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map upload(MultipartFile file) {

        try {
            return this.cloudinary.uploader().upload(file.getBytes(), Map.of());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
