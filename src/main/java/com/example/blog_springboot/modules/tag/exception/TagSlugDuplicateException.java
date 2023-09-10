package com.example.blog_springboot.modules.tag.exception;

public class TagSlugDuplicateException extends RuntimeException{


    public TagSlugDuplicateException(String message){
        super(message);
    }
}
