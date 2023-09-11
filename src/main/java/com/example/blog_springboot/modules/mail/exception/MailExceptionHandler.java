package com.example.blog_springboot.modules.mail.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import jakarta.mail.SendFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MailExceptionHandler {
    @ExceptionHandler(SendFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse sendFailedExceptionHandler(SendFailedException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Gửi mail thất bại");
    }
}
