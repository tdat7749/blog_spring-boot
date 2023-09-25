package com.example.blog_springboot.modules.user.exception;

public class InvalidCodeException extends RuntimeException{
    public InvalidCodeException(String message){
        super(message);
    }
}
