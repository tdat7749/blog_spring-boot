package com.example.blog_springboot.modules.post.viewmodel;

import com.example.blog_springboot.modules.tag.viewmodel.TagVm;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;

import java.util.List;

public class PostVm {
    private int id;
    private String title;
    private String content;
    private String slug;
    private String summary;
    private String thumbnail;
    private int isPublished;
    private String createdAt;
    private String updatedAt;
    private List<TagVm> tags;
    private UserVm author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(int isPublished) {
        this.isPublished = isPublished;
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
}
