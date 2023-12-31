package com.example.blog_springboot.modules.filestorage.service;


import com.cloudinary.Cloudinary;
import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.filestorage.exception.FileIsEmptyException;
import com.example.blog_springboot.modules.filestorage.exception.FileTooLargeException;
import com.example.blog_springboot.modules.filestorage.exception.NotAllowMimeTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.tika.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final Cloudinary cloudinary;

    public FileStorageServiceImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @Override
    public SuccessResponse<String> uploadFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            throw new FileIsEmptyException("File trống !");
        }

        if(multipartFile.getSize() > Constants.MAX_FILE){ // > 3mb
            throw new FileTooLargeException("Dung lượng file vượt quá 3mb");
        }

        Tika tika = new Tika();

        String detectedMineType = tika.detect(multipartFile.getInputStream());
        if(!Arrays.asList(Constants.MIME_TYPES).contains(detectedMineType)){
            throw new NotAllowMimeTypeException("Chỉ chấp nhận .png, .webp, .jpg, .jpeg");
        }

        var fileUrl = cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();

        return new SuccessResponse<>("Thành công",fileUrl);
    }
}
