package com.example.blog_springboot.modules.likepost.exception;

public class NotYetLikedPostException extends RuntimeException{
    public NotYetLikedPostException(String message){
        super(message);
    }
}
