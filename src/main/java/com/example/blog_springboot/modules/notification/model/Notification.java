package com.example.blog_springboot.modules.notification.model;

import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_read",nullable = false)
    private boolean isRead;

    @Column(nullable = false,length = 1000)
    private String message;

    @ManyToMany(mappedBy = "notifications")
    @JsonBackReference
    private List<User> users;

    public Notification(int id, boolean isRead, String message) {
        this.id = id;
        this.isRead = isRead;
        this.message = message;
    }

    public Notification() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
