package com.example.blog_springboot.modules.filestorage.exception;

public class FileIsEmptyException extends RuntimeException{
    public FileIsEmptyException(String message){
        super(message);
    }
}
