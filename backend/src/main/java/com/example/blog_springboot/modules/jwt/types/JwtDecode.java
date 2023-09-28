package com.example.blog_springboot.modules.jwt.types;

public class JwtDecode {
    private int userId;
    private String role;
    private String userName;

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public String getUserName(){return userName;}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserName(String userName){this.userName = userName;}
}
