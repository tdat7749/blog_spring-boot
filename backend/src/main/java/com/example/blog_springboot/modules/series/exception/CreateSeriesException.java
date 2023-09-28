package com.example.blog_springboot.modules.series.exception;

public class CreateSeriesException extends RuntimeException{
    public CreateSeriesException(String message){
        super(message);
    }
}
