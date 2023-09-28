package com.example.blog_springboot.modules.follow.model;

import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    @JsonBackReference
    private User followers;

    @ManyToOne
    @JoinColumn(name = "following_id")
    @JsonBackReference
    private User following;

    public Follow(int id, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Follow(){

    }

    public int getId() {
        return id;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User getFollowers() {
        return followers;
    }

    public User getFollowing() {
        return following;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setFollowers(User followers) {
        this.followers = followers;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
