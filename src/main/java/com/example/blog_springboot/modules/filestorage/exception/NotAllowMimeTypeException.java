package com.example.blog_springboot.modules.filestorage.exception;

public class NotAllowMimeTypeException extends RuntimeException{
    public NotAllowMimeTypeException(String message){
        super(message);
    }
}
