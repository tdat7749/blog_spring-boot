package com.example.blog_springboot.modules.notification.service;

import com.example.blog_springboot.modules.notification.constant.NotificationConstants;
import com.example.blog_springboot.modules.notification.dto.CreateNotificationDTO;
import com.example.blog_springboot.modules.notification.exception.CreateNotificationException;
import com.example.blog_springboot.modules.notification.model.Notification;
import com.example.blog_springboot.modules.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }
    @Override
    @Transactional
    public Notification createNotification(CreateNotificationDTO dto) {
        Notification noti = new Notification();
        noti.setLink(dto.getLink());
        noti.setMessage(dto.getMessage());
        noti.setCreatedAt(new Date());
        noti.setUpdatedAt(new Date());

        var save = notificationRepository.save(noti);
        if(save == null){
            throw new CreateNotificationException(NotificationConstants.CREATE_NOTIFICATION_FAILED);
        }
        return save;
    }
}
