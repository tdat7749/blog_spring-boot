package com.example.blog_springboot.modules.filestorage.exception;

public class FileTooLargeException extends RuntimeException{
    public FileTooLargeException(String message){
        super(message);
    }
}
