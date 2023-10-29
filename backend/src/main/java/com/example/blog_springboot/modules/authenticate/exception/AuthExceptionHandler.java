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
    public ErrorResponse emailUsedExceptionHandler(EmailUsedException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(UserNameUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userNameUsedExceptionHandler(UserNameUsedException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(RegisterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse registerExceptionHandler(RegisterException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse loginExceptionHandler(AuthenticationException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse accountLockedExceptionHandler(AccountLockedException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse disabledExceptionHandler(DisabledException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, "Tài khoản chưa được xác thực");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFoundExceptionHandler(UserNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, 404, ex.getMessage());
    }

    @ExceptionHandler(VerifyEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse verifyEmailExceptionHandler(VerifyEmailException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }

    @ExceptionHandler(SetCodeUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse setCodeUserExceptionHandler(SetCodeUserException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 400, ex.getMessage());
    }
}
