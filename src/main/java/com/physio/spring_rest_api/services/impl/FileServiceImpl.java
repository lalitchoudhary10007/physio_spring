package com.physio.spring_rest_api.services.impl;

import com.physio.spring_rest_api.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String uploadDirectory, MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @Override
    public InputStream getResource(String imageDirectory, String imageName) throws FileNotFoundException {
        Path imagePath = Path.of(imageDirectory, imageName);
        InputStream inputStream = null;
        if (Files.exists(imagePath)){
            inputStream = new FileInputStream(imagePath.toString());
        }else {
            throw new FileNotFoundException("Request image not found");
        }
        return inputStream;
    }
}
