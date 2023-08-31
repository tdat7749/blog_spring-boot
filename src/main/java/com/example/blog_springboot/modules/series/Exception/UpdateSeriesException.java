package com.example.blog_springboot.modules.series.Exception;

public class UpdateSeriesException extends RuntimeException{
    public UpdateSeriesException(String message){
        super(message);
    }
}
