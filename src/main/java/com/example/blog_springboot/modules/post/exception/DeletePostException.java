package com.example.blog_springboot.modules.post.exception;

public class DeletePostException extends RuntimeException{
    public DeletePostException(String message){
        super(message);
    }
}
