package com.example.blog_springboot.modules.user.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(PasswordIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse passwordIncorrectExceptionHandler(PasswordIncorrectException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse passwordNotMatchExceptionHandler(PasswordNotMatchException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(ChangePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse changePasswordExceptionHandler(ChangePasswordException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(ChangeAvatarException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse changeAvatarExceptionHandler(ChangeAvatarException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(ChangeUserInformationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse changeUserInformationExceptionHandler(ChangeUserInformationException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(InvalidRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidRoleExceptionHandler(InvalidRoleException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(ChangePermissionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse changePermissionExceptionHandler(ChangePermissionException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(InvalidCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidCodeExceptionHandler(InvalidCodeException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(ForgotPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse forgotPasswordExceptionHandler(ForgotPasswordException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }

    @ExceptionHandler(SendMailForgotPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse sendMailForgotPasswordExceptionHandler(SendMailForgotPasswordException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, ex.getMessage());
    }
}
