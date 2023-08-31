package com.example.blog_springboot.modules.series.Exception;

public class CreateSeriesException extends RuntimeException{
    public CreateSeriesException(String message){
        super(message);
    }
}
