package com.example.blog_springboot.modules.series.exception;

public class DeleteSeriesException extends RuntimeException{
    public DeleteSeriesException(String message){
        super(message);
    }
}
