package com.example.blog_springboot.modules.filestorage.controller;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.filestorage.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;

@RestController
@RequestMapping("/api/filestorage")
public class FileStorageController {
    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<SuccessResponse<String>> uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        var result = fileStorageService.uploadFile(multipartFile);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
