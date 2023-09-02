package com.example.blog_springboot.modules.authenticate.exception;

public class RegisterException extends RuntimeException{
    public RegisterException(String message){
        super(message);
    }
}
