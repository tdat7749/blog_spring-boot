package com.example.blog_springboot.modules.series.exception;

public class SeriesNotFoundException extends RuntimeException{
    public SeriesNotFoundException(String message){
        super(message);
    }
}
