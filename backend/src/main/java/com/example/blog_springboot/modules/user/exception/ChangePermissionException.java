package com.example.blog_springboot.modules.user.exception;

public class ChangePermissionException extends RuntimeException{
    public ChangePermissionException(String message){
        super(message);
    }
}
