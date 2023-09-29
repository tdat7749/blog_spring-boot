package com.example.blog_springboot.modules.user.exception;

public class ChangeAvatarException extends RuntimeException{
    public ChangeAvatarException(String message){
        super(message);
    }
}
