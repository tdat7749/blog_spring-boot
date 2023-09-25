package com.example.blog_springboot.modules.user.exception;

public class InvalidRoleException extends RuntimeException{
    public InvalidRoleException(String message){
        super(message);
    }
}
