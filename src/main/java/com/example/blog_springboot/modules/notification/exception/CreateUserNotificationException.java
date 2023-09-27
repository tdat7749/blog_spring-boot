package com.example.blog_springboot.modules.notification.exception;

public class CreateUserNotificationException extends RuntimeException{
    public CreateUserNotificationException(String message){
        super(message);
    }
}
