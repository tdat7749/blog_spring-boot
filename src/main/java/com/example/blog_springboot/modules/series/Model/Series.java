package com.example.blog_springboot.modules.series.Model;

import com.example.blog_springboot.modules.post.Model.Post;
import com.example.blog_springboot.modules.user.Model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100,nullable = false)
    private String title;


    // Config ORM

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "series")
    @JsonManagedReference
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    //

    public Series(){

    }
    public Series(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
