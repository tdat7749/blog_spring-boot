package com.example.blog_springboot.modules.post.exception;

public class RemovePostFromSeriesException  extends RuntimeException{
    public RemovePostFromSeriesException(String message){
        super(message);
    }
}
