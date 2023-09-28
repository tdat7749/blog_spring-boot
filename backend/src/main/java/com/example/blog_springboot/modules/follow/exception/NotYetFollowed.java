package com.example.blog_springboot.modules.follow.exception;

public class NotYetFollowed extends RuntimeException{
    public NotYetFollowed(String message){
        super(message);
    }
}
