package com.jyhun.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class LocalStorageService {

    private final String uploadDirectory = "C:/Image/";

    public String saveImageToLocal(MultipartFile photo) {
        try {
            // Check if upload directory exists, if not, create it
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a unique file name to avoid conflicts
            String originalFileName = photo.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;

            // Define the full path where the file will be stored
            File file = new File(directory, uniqueFileName);

            // Save the file to the local storage
            photo.transferTo(file);

            // Return the relative path or URL to the uploaded file
            return "/uploads/" + uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("Unable to upload image: " + e.getMessage());
        }
    }
}
