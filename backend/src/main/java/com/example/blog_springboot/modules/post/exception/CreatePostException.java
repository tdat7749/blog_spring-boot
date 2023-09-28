package com.example.blog_springboot.modules.post.exception;

public class CreatePostException extends RuntimeException{
    public CreatePostException(String message){
        super(message);
    }
}
