package com.example.blog_springboot.modules.post.exception;

public class UpdatePostException extends RuntimeException{
    public UpdatePostException(String message){
        super(message);
    }
}
