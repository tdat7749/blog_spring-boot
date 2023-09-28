package com.example.blog_springboot.modules.tag.exception;

public class UpdateTagException extends RuntimeException{
    public UpdateTagException(String message){
        super(message);
    }
}
