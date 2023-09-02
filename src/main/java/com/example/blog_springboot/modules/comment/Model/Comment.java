package com.example.blog_springboot.modules.comment.Model;

import com.example.blog_springboot.modules.post.Model.Post;
import com.example.blog_springboot.modules.user.model.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 300,nullable = false)
    private String content;

    @Column(name = "parent_id",nullable = true)
    private int parentId;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
    private Date updatedAt;


    // Config ORM
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getParentId() {
        return parentId;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment() {

    }
    public Comment(int id, String content, int parentId) {
        this.id = id;
        this.content = content;
        this.parentId = parentId;
    }

}
