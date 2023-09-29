package com.example.blog_springboot.modules.authenticate.exception;

public class SetCodeUserException extends RuntimeException{
    public SetCodeUserException(String message){
        super(message);
    }
}
