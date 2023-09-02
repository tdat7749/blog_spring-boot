package com.example.blog_springboot.modules.post.Model;


import com.example.blog_springboot.modules.comment.Model.Comment;
import com.example.blog_springboot.modules.posttags.Model.PostTags;
import com.example.blog_springboot.modules.series.Model.Series;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 130,nullable = false)
    private String slug;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String content;

    @Column(length = 255, nullable = false)
    private String summary;

    @Column(name = "is_published",nullable = false)
    @ColumnDefault("false")
    private boolean isPublished;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;


    // Config ORM
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "post")
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "post")
    @JsonManagedReference
    private List<PostTags> postTags;

    @ManyToOne
    @JoinColumn(nullable = true,name = "series_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    //

    public Post() {
    }

    public Post(int id, String title, String content, boolean isPublished, Date createdAt, Date updatedAt,String summary) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isPublished() {
        return isPublished;
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

    public List<Comment> getComments() {
        return comments;
    }

    public List<PostTags> getPostTags() {
        return postTags;
    }

    public Series getSeries() {
        return series;
    }

    public User getUser() {
        return user;
    }

    public String getSummary() {
        return summary;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublished(boolean published) {
        isPublished = published;
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setPostTags(List<PostTags> postTags) {
        this.postTags = postTags;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setSummary(String summary) {
        this.summary = summary;
    }
}

