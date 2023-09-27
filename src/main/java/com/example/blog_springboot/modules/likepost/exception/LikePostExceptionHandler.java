package com.example.blog_springboot.modules.likepost.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LikePostExceptionHandler {

    @ExceptionHandler(LikePostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse likePostException(LikePostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(UnLikePostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse unLikePostException(UnLikePostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(LikedPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse likedPostException(LikedPostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(NotYetLikedPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse notYetLikedPostException(NotYetLikedPostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }
}
