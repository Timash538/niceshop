package com.niceshop.utils;

import java.io.*;
import java.nio.file.*;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String filename,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IOException("Could not save image file: " + filename, exception);
        }
    }

    public static void deleteFile(String uploadDir, String filename) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(filename);
        System.out.println("EXISTS???");
        if (Files.exists(filePath)) {
            System.out.println("EXISTS");
            Files.delete(filePath);
        }
    }
}
