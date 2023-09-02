package com.example.blog_springboot.modules.jwt.exception;

import com.example.blog_springboot.commons.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidKeyException;
import java.security.SignatureException;

@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler(InvalidKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidKeyExceptionHandler(InvalidKeyException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Key không hợp lệ");
    }

    @ExceptionHandler(DecodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidKeyExceptionHandler(DecodingException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Decoding lỗi");
    }


    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse expiredJwtExceptionHandler(DecodingException ex){
        return new ErrorResponse(HttpStatus.UNAUTHORIZED,401,"Phiên đăng nhập hết hạn");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse unsupportedJwtExceptionHandler(UnsupportedJwtException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Token có định dạng không đúng");
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse malformedJwtExceptionHandler(MalformedJwtException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Token có cấu trúc không phù hợp");
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse malformedJwtExceptionHandler(SignatureException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,"Chữ ký đi cùng không hợp lệ");
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse malformedJwtExceptionHandler(IllegalArgumentException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }
}
