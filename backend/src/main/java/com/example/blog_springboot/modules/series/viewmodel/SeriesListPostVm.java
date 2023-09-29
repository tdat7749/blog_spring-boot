package com.example.blog_springboot.modules.series.viewmodel;

import com.example.blog_springboot.modules.post.viewmodel.PostListVm;

import java.util.List;

public class SeriesListPostVm {
    private int id;
    private String title;
    private String slug;
    private String content;

    private String updatedAt;
    private String createdAt;
    private List<PostListVm> posts;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getContent() {
        return content;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<PostListVm> getPosts() {
        return posts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setPosts(List<PostListVm> posts) {
        this.posts = posts;
    }
}
