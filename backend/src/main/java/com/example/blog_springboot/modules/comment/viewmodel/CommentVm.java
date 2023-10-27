package com.example.blog_springboot.modules.comment.viewmodel;

import com.example.blog_springboot.modules.user.viewmodel.UserVm;

import java.util.ArrayList;
import java.util.List;

public class CommentVm {
    private int id;
    private int parentId;
    private String content;
    private UserVm user;
    private String createdAt;

    private List<CommentVm> childComment = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserVm getUser() {
        return user;
    }

    public void setUser(UserVm user) {
        this.user = user;
    }

    public List<CommentVm> getChildComment() {
        return childComment;
    }

    public void setChildComment(List<CommentVm> childComment) {
        this.childComment = childComment;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
