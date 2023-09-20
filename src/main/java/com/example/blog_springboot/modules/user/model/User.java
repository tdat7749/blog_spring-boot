package com.example.blog_springboot.modules.user.model;


import com.example.blog_springboot.modules.comment.Model.Comment;
import com.example.blog_springboot.modules.likepost.model.LikePost;
import com.example.blog_springboot.modules.notification.model.Notification;
import com.example.blog_springboot.modules.post.Model.Post;
import com.example.blog_springboot.modules.series.model.Series;
import com.example.blog_springboot.modules.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users",indexes = {
        @Index(name = "idx_username",columnList = "user_name"),
        @Index(name = "idx_email",columnList = "email")
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name",length = 60,nullable = false)
    private String firstName;

    @Column(name = "last_name",length = 70,nullable = false)
    private String lastName;

    @Column(unique = true,nullable = false)
    private String email;

    @ColumnDefault("false")
    @Column(name = "is_verify",nullable = false)
    private boolean isVerify;

    @Column(nullable = false,length = 100)
    private String password;

    @ColumnDefault("true")
    @Column(name = "is_not_locked",nullable = false)
    private boolean isNotLocked;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String avatar;

    @Column(name = "user_name",nullable = false,length = 50,unique = true)
    private String userName;

    @Column(name = "created_at",nullable = false)
    private Date createdAt;

    @Column(name="updated_at",nullable = false)
    private Date updatedAt;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private String code;

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

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user")
    @JsonBackReference
    private List<LikePost> likePosts;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    @JsonManagedReference
    private List<User> following;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "following")
    @JsonManagedReference
    private List<User> followers;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    @JsonManagedReference
    private List<Notification> notifications;


    //

    public User(){

    }
    public User(int id, String firstName, String lastName, String email, boolean isVerify, String password, boolean isNotLocked, String avatar, String userName, Date createdAt, Date updatedAt,Role role,String code) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isVerify = isVerify;
        this.password = password;
        this.isNotLocked = isNotLocked;
        this.avatar = avatar;
        this.userName = userName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public boolean isNotLocked() {
        return isNotLocked;
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

    public void setNotLocked(boolean isNotLocked) {
        this.isNotLocked = isNotLocked;
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

    public void setRole(Role role) {
        this.role = role;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerify;
    }

    public String getAvatar() {
        return avatar;
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

    public Role getRole() {
        return role;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
