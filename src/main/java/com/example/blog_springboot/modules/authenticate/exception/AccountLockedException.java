package com.example.blog_springboot.modules.authenticate.exception;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(String message){
        super(message);
    }
}
