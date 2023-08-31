package com.example.blog_springboot.exceptions;


import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(NotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND, 404,ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidHandler(MethodArgumentNotValidException ex){
        String defaultMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, defaultMessage);
    }
}
