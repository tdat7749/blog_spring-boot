package com.example.blog_springboot.modules.comment.exception;


public class CreateCommentException extends RuntimeException{
    public CreateCommentException(String message){
        super(message);
    }
}
