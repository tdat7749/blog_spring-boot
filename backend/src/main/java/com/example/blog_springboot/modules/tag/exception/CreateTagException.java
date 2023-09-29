package com.example.blog_springboot.modules.tag.exception;

public class CreateTagException extends RuntimeException{
    public CreateTagException(String message){
        super(message);
    }
}
