package com.example.blog_springboot.modules.authenticate.exception;

import com.example.blog_springboot.commons.ErrorResponse;

public class EmailUsedException extends RuntimeException{
    public EmailUsedException(String message){
        super(message);
    }
}
