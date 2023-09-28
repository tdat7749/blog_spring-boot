package com.example.blog_springboot.modules.post.viewmodel;

import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;

import java.util.List;

public class PostListVm {
    private String title;
    private String slug;
    private int id;
    private String createdAt;
    private String updatedAt;
    private List<TagVm> tags;
    private UserVm author;

    private String thumbnail;
    private String summary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TagVm> getTags() {
        return tags;
    }

    public void setTags(List<TagVm> tags) {
        this.tags = tags;
    }

    public UserVm getAuthor() {
        return author;
    }

    public void setAuthor(UserVm author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
