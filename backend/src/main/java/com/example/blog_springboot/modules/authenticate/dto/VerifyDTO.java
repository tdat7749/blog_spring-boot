package com.example.blog_springboot.modules.authenticate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VerifyDTO {
    @NotNull(message = "Không được thiếu trường 'email'")
    @NotBlank(message = "Không được để trống trường 'email'")
    private String email;
    @NotNull(message = "Không được thiếu trường 'code'")
    @NotBlank(message = "Không được để trống trường 'code'")
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
