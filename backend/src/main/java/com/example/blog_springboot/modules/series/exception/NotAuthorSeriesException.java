package com.example.blog_springboot.modules.series.exception;

public class NotAuthorSeriesException extends RuntimeException{
    public NotAuthorSeriesException(String message){
        super(message);
    }
}
