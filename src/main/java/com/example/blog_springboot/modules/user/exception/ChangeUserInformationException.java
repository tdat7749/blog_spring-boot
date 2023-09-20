package com.example.blog_springboot.modules.user.exception;

public class ChangeUserInformationException extends RuntimeException{
    public ChangeUserInformationException(String message){
        super(message);
    }
}
