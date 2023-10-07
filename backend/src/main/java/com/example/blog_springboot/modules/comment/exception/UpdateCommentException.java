package com.example.blog_springboot.modules.comment.exception;

public class UpdateCommentException extends RuntimeException{
    public UpdateCommentException(String message){
        super(message);
    }
}
