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
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFileName = photo.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;

            File file = new File(directory, uniqueFileName);

            photo.transferTo(file);
            
            return "/uploads/" + uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패: " + e.getMessage());
        }
    }
}
