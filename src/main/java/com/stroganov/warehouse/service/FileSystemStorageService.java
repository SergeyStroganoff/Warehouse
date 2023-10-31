package com.stroganov.warehouse.service;


import com.stroganov.warehouse.exception.StorageException;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@NoArgsConstructor
public class FileSystemStorageService implements StorageService {
    public static final String FAILED_TO_STORE_EMPTY_FILE = "Failed to store empty file.";
    private final Path currentTempDir = Path.of(System.getProperty("java.io.tmpdir"));

    @Override
    public Path store(MultipartFile multiPartFile) throws StorageException {
        Path destinationFilePath;
        if (multiPartFile == null || multiPartFile.isEmpty()) {
            throw new StorageException(FAILED_TO_STORE_EMPTY_FILE);
        }
        try {
            destinationFilePath = createTempFile(multiPartFile);
        } catch (IOException e) {
            throw new StorageException("Can't create file in storage");
        }
        try (InputStream inputStream = multiPartFile.getInputStream()) {
            Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
        return destinationFilePath;
    }

    @Override
    public void delete(Path file) throws StorageException {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    @Override
    public Path load(String filename) {
        return currentTempDir.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws StorageException {
        String errorMessage = String.format("Could not read file: %s ", filename);
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException(errorMessage);

            }
        } catch (MalformedURLException e) {
            throw new StorageException(errorMessage, e);
        }
    }

    public Path createTempFile(MultipartFile multiPartFile) throws IOException, StorageException {
        String originalFileName = multiPartFile.getOriginalFilename();
        if (originalFileName == null) {
            throw new StorageException(FAILED_TO_STORE_EMPTY_FILE);
        }
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return Files.createTempFile(currentTempDir, "uploadedExelTemp", extension);
    }
}
