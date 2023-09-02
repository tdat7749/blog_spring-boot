package com.example.blog_springboot.modules.authenticate.viewmodel;

public class AuthenVm {
    private String accessToken;
    private String refeshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefeshToken() {
        return refeshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefeshToken(String refeshToken) {
        this.refeshToken = refeshToken;
    }
}
