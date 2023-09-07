package com.stroganov.warehouse.service;

import com.stroganov.warehouse.exception.StorageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FileSystemStorageServiceTest {

    @Mock
    MultipartFile multipartFile;

    @InjectMocks
    FileSystemStorageService fileSystemStorageService;

    @Test
    void store() {
    }

    @Test
    void load() {
    }

    @Test
    void createTempFileTest() throws IOException, StorageException {
        // given  - Mock data
        String exelFileName = "sample.xls";
        when(multipartFile.getOriginalFilename()).thenReturn(exelFileName);
        // when
        Path path = fileSystemStorageService.createTempFile(multipartFile);
        System.out.println(path);
        //then
        File file = new File(path.toString());
        assertThat(file).exists();
        //verify
    }
}