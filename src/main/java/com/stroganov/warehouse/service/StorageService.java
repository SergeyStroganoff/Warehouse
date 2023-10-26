package com.stroganov.warehouse.service;

import com.stroganov.warehouse.exception.StorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    Path store(MultipartFile file) throws StorageException;

    void delete(Path file) throws StorageException;

    Path load(String filename);

    Resource loadAsResource(String filename) throws StorageException;
}
