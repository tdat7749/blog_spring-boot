package com.example.blog_springboot.modules.series.Exception;

public class DeleteSeriesException extends RuntimeException{
    public DeleteSeriesException(String message){
        super(message);
    }
}
