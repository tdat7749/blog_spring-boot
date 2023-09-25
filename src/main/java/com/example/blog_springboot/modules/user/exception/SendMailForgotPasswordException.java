package com.example.blog_springboot.modules.user.exception;

public class SendMailForgotPasswordException extends RuntimeException{
    public SendMailForgotPasswordException(String message){
        super(message);
    }
}
