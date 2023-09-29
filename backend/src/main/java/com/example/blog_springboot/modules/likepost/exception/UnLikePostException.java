package com.example.blog_springboot.modules.likepost.exception;

public class UnLikePostException extends RuntimeException{
    public UnLikePostException(String message){
        super(message);
    }
}
