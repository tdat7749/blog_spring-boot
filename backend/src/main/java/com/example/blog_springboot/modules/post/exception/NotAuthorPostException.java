package com.example.blog_springboot.modules.post.exception;

public class NotAuthorPostException extends RuntimeException{
    public NotAuthorPostException(String message){
        super(message);
    }
}
