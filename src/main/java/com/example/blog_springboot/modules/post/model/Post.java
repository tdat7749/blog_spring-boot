package com.example.blog_springboot.modules.post.model;


import com.example.blog_springboot.modules.comment.Model.Comment;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.tag.model.Tag;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts",indexes = {
        @Index(name = "idx_slug",columnList = "slug")
})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 130,nullable = false,unique = true)
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

    @Column(length = 1000, nullable = false)
    private String thumbnail;


    // Config ORM
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "post")
    @JsonManagedReference
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "posttags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonManagedReference
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(nullable = true,name = "series_id")
    @JsonBackReference
    private Series series;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    private User user;

    //

    public Post() {
    }

    public Post(int id, String title, String content, boolean isPublished, Date createdAt, Date updatedAt,String summary,String thumbnail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.summary = summary;
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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

