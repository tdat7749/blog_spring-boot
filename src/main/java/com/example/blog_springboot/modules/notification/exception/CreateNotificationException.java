package com.example.blog_springboot.modules.notification.exception;

public class CreateNotificationException extends RuntimeException{
    public CreateNotificationException(String message){
        super(message);
    }
}
