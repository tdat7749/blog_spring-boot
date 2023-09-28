package com.example.blog_springboot.modules.user.dto;

import com.example.blog_springboot.modules.user.enums.Role;

public class ChangePermissionDTO {
    private int userId;
    private Role role;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
