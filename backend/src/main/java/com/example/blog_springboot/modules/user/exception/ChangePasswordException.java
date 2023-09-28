package com.example.blog_springboot.modules.user.exception;

public class ChangePasswordException extends RuntimeException{
    public ChangePasswordException(String message){
        super(message);
    }
}
