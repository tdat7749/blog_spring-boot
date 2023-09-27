package com.example.blog_springboot.modules.notification.service;

import com.example.blog_springboot.modules.notification.model.Notification;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserNotificationService {
    public boolean createUserNotification(List<User> listUser, Notification notification);
}
