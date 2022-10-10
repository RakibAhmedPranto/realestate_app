package com.rea.app.common.fileService;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadFile(String path, MultipartFile file) throws IOException;

    Boolean deleteFile(String path, String fileName) throws IOException;
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
