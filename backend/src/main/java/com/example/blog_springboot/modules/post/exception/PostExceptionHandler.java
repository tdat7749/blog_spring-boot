package com.example.blog_springboot.modules.post.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler {
    @ExceptionHandler(CreatePostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse createPostExceptionHandler(CreatePostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(UpdatePostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse updatePostExceptionHandler(UpdatePostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(DeletePostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse deletePostExceptionHandler(DeletePostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(PostSlugDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse postSlugDuplicateExceptionHandler(PostSlugDuplicateException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(NotAuthorPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse notAuthorPostExceptionHandler(NotAuthorPostException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse postNotFoundExceptionHandler(PostNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND, 404, ex.getMessage());
    }

    @ExceptionHandler(MaxTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse maxTagExceptionHandler(MaxTagException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 404, ex.getMessage());
    }
}
