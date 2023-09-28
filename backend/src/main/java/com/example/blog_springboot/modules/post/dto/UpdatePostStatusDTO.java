package com.example.blog_springboot.modules.post.dto;

import jakarta.validation.constraints.NotNull;

public class UpdatePostStatusDTO {

    @NotNull(message = "Trường 'status' không được null")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
