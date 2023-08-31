package com.example.blog_springboot.modules.series.Exception;

public class SeriesSlugDuplicateException extends RuntimeException{
    public SeriesSlugDuplicateException(String message){
        super(message);
    }
}
