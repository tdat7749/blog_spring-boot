package com.example.blog_springboot.modules.user.exception;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(String message){
        super(message);
    }
}
