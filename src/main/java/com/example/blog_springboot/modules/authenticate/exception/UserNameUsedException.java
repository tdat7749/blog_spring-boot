package com.example.blog_springboot.modules.authenticate.exception;

public class UserNameUsedException extends RuntimeException{
    public UserNameUsedException(String message){
        super(message);
    }
}
