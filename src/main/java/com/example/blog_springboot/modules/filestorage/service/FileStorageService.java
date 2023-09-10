package com.example.blog_springboot.modules.filestorage.service;

import com.example.blog_springboot.commons.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileStorageService {
    public SuccessResponse<String> uploadFile(MultipartFile multipartFile) throws IOException;
}
