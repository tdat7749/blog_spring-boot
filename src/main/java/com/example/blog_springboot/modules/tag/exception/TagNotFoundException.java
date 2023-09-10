package com.example.blog_springboot.modules.tag.exception;

public class TagNotFoundException extends RuntimeException{
    public TagNotFoundException(String message){
        super(message);
    }
}
