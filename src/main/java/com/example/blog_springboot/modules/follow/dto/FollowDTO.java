package com.example.blog_springboot.modules.follow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FollowDTO {

    @NotBlank(message = "Không được bỏ trống trường 'followerId'")
    @NotNull(message = "Không được thiếu trường 'followerId'")
    private int followerId;

    @NotBlank(message = "Không được bỏ trống trường 'followerId'")
    @NotNull(message = "Không được thiếu trường 'followerId'")
    private int followingId;

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }
}
