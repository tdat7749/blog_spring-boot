package com.example.blog_springboot.modules.authenticate.exception;

public class VerifyEmailException extends RuntimeException{
    public VerifyEmailException(String message){
        super(message);
    }
}
