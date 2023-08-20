package com.example.blog_springboot.modules.user.Model;


import com.example.blog_springboot.modules.comment.Model.Comment;
import com.example.blog_springboot.modules.post.Model.Post;
import com.example.blog_springboot.modules.series.Model.Series;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name",length = 40,nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 50,nullable = false)
    private String lastName;

    @Column(unique = true,nullable = false)
    private String email;

    @ColumnDefault("false")
    @Column(name = "is_verify")
    private boolean isVerify;

    @Column(nullable = false,length = 100)
    private String password;

    @ColumnDefault("false")
    @Column(name = "is_banned")
    private boolean isBanned;

    private String avatar;

    @Column(name = "user_name",nullable = false,length = 50,unique = true)
    private String userName;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @Column(name="updated_at",nullable = false)
    private Date updatedAt;

    // Config ORM

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    @JsonManagedReference
    private List<Post> posts;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    @JsonManagedReference
    private List<Series> series;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> following;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "following")
    private List<User> followers;

    //

    public User(){

    }
    public User(int id, String firstName, String lastName, String email, boolean isVerify, String password, boolean isBanned, String avatar, String userName, Date createdAt, Date updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isVerify = isVerify;
        this.password = password;
        this.isBanned = isBanned;
        this.avatar = avatar;
        this.userName = userName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

//    public void setFollowing(List<User> following) {
//        this.following = following;
//    }
//
//    public void setFollowers(List<User> followers) {
//        this.followers = followers;
//    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUserName() {
        return userName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Series> getSeries() {
        return series;
    }

//    public List<User> getFollowing() {
//        return following;
//    }
//
//    public List<User> getFollowers() {
//        return followers;
//    }
}
