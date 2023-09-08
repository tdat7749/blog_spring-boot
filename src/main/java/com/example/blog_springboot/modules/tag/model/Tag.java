package com.example.blog_springboot.modules.tag.model;


import com.example.blog_springboot.modules.post.Model.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tags",indexes = {
        @Index(name = "idx_slug",columnList = "slug")
})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 1000,nullable = false)
    private String thumbnail;

    @Column(length = 120,nullable = false,unique = true)
    private String slug;

    @ColumnDefault("false")
    private boolean status;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;

    //Config ORM

    @ManyToMany(mappedBy = "tags")
    @JsonBackReference
    private List<Post> posts;

    public Tag(){

    }

    public Tag(int id, String title, String thumbnail, boolean status, Date createdAt, Date updatedAt,String slug) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.slug = slug;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public boolean isStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    public String getSlug() {
        return slug;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public void setSlug(String slug) {
        this.slug = slug;
    }
}
