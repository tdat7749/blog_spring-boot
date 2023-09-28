package com.example.blog_springboot.modules.notification.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationExceptionHandler {
    @ExceptionHandler(CreateNotificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse createNotificationExceptionHandler(CreateNotificationException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(CreateUserNotificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse createUserNotificationExceptionHandler(CreateUserNotificationException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notificationNotFoundExceptionHandler(NotificationNotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND,404,ex.getMessage());
    }

    @ExceptionHandler(ReadNotificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse readNotificationExceptionHandler(ReadNotificationException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }
}
