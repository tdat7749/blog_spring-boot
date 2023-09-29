package com.example.blog_springboot.modules.notification.model;

import com.example.blog_springboot.modules.user.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user_notifications")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "notification_id",nullable = false)
    private Notification notification;

    @Column(name = "is_read",nullable = false)
    @ColumnDefault(value = "false")
    private boolean isRead;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public UserNotification(){

    }

    public UserNotification(int id, User user, Notification notification, boolean isRead) {
        this.id = id;
        this.user = user;
        this.notification = notification;
        this.isRead = isRead;
    }
}
