package com.example.blog_springboot.modules.series.ViewModel;

public class SeriesVm {
    private int id;
    private String title;

    private String slug;

    private String content;

    private String updatedAt;

    private String createdAt;

    public SeriesVm(){

    }

    public SeriesVm(int id, String title, String slug, String content, String updatedAt, String createdAt) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

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
}
