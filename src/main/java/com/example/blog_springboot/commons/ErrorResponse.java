package com.example.blog_springboot.commons;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private HttpStatus httpStatus;
    private Integer httpStatusCode;
    private String message;

    public ErrorResponse(HttpStatus httpStatus, Integer httpStatusCode, String message) {
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
