package com.example.blog_springboot.modules.comment.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentExceptionHandler {

    @ExceptionHandler(CreateCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse createCommentExceptionHandler(CreateCommentException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(UpdateCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse updateCommentExceptionHandler(UpdateCommentException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }
    @ExceptionHandler(NotAuthorCommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse notAuthorCommentExceptionHandler(NotAuthorCommentException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse commentNotFoundExceptionHandler(CommentNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND,404,ex.getMessage());
    }

}
