package com.example.blog_springboot.modules.notification.exception;

public class ReadNotificationException extends RuntimeException{
    public ReadNotificationException(String message){
        super(message);
    }
}
