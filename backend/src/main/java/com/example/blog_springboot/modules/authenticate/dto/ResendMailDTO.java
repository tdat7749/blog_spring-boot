package com.example.blog_springboot.modules.authenticate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ResendMailDTO {
    @NotBlank(message = "Không được bỏ trống trường 'email'")
    @NotNull(message = "Không được thiếu trường 'email'")
    @Email(message = "Email không đúng định dạng")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
