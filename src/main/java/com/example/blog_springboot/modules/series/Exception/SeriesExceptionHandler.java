package com.example.blog_springboot.modules.series.Exception;

import com.example.blog_springboot.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SeriesExceptionHandler {

    @ExceptionHandler(CreateSeriesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse createSeriesExceptionHandler(CreateSeriesException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(UpdateSeriesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse updateSeriesExceptionHandler(UpdateSeriesException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }

    @ExceptionHandler(DeleteSeriesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse deleteSeriesExceptionHandler(DeleteSeriesException ex){
        return new ErrorResponse(HttpStatus.BAD_REQUEST,400,ex.getMessage());
    }
}
