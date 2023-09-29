package com.example.blog_springboot.modules.authenticate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginDTO {

    @NotBlank(message = "Không được bỏ trống trường 'userName'")
    @NotNull(message = "Không được thiếu trường 'userName'")
    private String userName;

    @NotBlank(message = "Không được bỏ trống trường 'password'")
    @NotNull(message = "Không được thiếu trường 'password'")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
