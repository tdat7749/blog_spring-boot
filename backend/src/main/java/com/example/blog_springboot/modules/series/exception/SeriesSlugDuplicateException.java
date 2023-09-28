package com.example.blog_springboot.modules.series.exception;

public class SeriesSlugDuplicateException extends RuntimeException{
    public SeriesSlugDuplicateException(String message){
        super(message);
    }
}
