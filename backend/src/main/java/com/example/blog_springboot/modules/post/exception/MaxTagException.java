package com.example.blog_springboot.modules.post.exception;

public class MaxTagException extends RuntimeException{
    public MaxTagException(String message){
        super(message);
    }
}
