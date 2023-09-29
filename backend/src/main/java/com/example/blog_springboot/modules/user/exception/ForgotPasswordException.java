package com.example.blog_springboot.modules.user.exception;

public class ForgotPasswordException extends RuntimeException{
    public ForgotPasswordException(String message){
        super(message);
    }
}
