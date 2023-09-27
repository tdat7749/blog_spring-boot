package com.example.blog_springboot.modules.notification.service;

import com.example.blog_springboot.modules.notification.dto.CreateNotificationDTO;
import com.example.blog_springboot.modules.notification.model.Notification;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    public Notification createNotification(CreateNotificationDTO dto);
}
