package com.example.blog_springboot.modules.filestorage.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.cloudinary.api.exceptions.BadRequest;
@RestControllerAdvice
public class FileStorageExceptionHandler {

    @ExceptionHandler(BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestUploadFileExceptionHandler(BadRequest ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Upload tệp thất bại");
    }

    @ExceptionHandler(FileIsEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse fileIsEmptyExceptionHandler(FileIsEmptyException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(FileTooLargeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse fileTooLargeExceptionHandler(FileTooLargeException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(NotAllowMimeTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse notAllowMimeTypeExceptionHandler(NotAllowMimeTypeException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse fileSizeLimitExceededExceptionHandler(FileSizeLimitExceededException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Dung lượng file vượt quá 3mb.");
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse SizeLimitExceededExceptionHandler(SizeLimitExceededException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Dung lượng request vượt quá 10mb");
    }
}
