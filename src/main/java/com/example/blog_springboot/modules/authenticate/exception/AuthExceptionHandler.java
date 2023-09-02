package com.example.blog_springboot.modules.authenticate.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(EmailUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse emailUsedExceptionHandler(EmailUsedException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(UserNameUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userNameUsedExceptionHandler(UserNameUsedException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(RegisterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse registerExceptionHandler(RegisterException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse loginExceptionHandler(AuthenticationException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse lockedExceptionHandler(LockedException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Tài khoản đã bị khóa");
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse disabledExceptionHandler(DisabledException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Tài khoản chưa được xác thực");
    }
}
