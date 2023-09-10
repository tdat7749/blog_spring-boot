package com.example.blog_springboot.modules.tag.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TagExceptionHandler {

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse tagNotFoundExceptionHandler(TagNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND,404,ex.getMessage());
    }

    @ExceptionHandler(TagSlugDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse tagSlugDuplicateExceptionHandler(TagSlugDuplicateException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }


    @ExceptionHandler(CreateTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse createTagExceptionHandler(CreateTagException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(UpdateTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse updateTagExceptionHandler(UpdateTagException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }
}
