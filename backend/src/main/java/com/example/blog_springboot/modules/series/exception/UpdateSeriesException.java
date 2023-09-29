package com.example.blog_springboot.modules.series.exception;

public class UpdateSeriesException extends RuntimeException{
    public UpdateSeriesException(String message){
        super(message);
    }
}
