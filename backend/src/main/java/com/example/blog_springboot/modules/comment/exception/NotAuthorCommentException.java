package com.example.blog_springboot.modules.comment.exception;

public class NotAuthorCommentException extends RuntimeException{
    public NotAuthorCommentException(String message){
        super(message);
    }
}
