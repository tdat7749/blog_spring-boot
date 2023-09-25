package com.example.blog_springboot.modules.post.exception;

public class PostSlugDuplicateException extends RuntimeException{
    public PostSlugDuplicateException(String message){
        super(message);
    }
}
