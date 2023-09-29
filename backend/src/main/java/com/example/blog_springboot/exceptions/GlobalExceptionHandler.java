package com.example.blog_springboot.exceptions;


import com.example.blog_springboot.commons.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(NotFoundException ex){
        return new ErrorResponse(HttpStatus.NOT_FOUND, 404,ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidHandler(MethodArgumentNotValidException ex){
        String defaultMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400, defaultMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse accessDeniedExceptionHandler(AccessDeniedException ex){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED,401,"Không có quyền truy cập");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse expiredJwtExceptionHandler(ExpiredJwtException ex){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED,401,"Phiên đăng nhập hết hạn");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse authenticationExceptionHandler(AuthenticationException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Sai tài khoản hoặc mật khẩu");
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse jsonProcessingExceptionHandler(JsonProcessingException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,500,"Lỗi xảy ra, vui lòng thử lại sau");
    }
}
