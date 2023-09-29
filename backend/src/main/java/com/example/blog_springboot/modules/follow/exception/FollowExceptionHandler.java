package com.example.blog_springboot.modules.follow.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FollowExceptionHandler {

    @ExceptionHandler(FollowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse followedException(FollowedException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(NotYetFollowed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse followedException(NotYetFollowed ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }
}
