package com.example.blog_springboot.modules.notification.service;

import com.example.blog_springboot.modules.notification.constant.NotificationConstants;
import com.example.blog_springboot.modules.notification.exception.CreateUserNotificationException;
import com.example.blog_springboot.modules.notification.model.Notification;
import com.example.blog_springboot.modules.notification.model.UserNotification;
import com.example.blog_springboot.modules.notification.repository.UserNotificationRepository;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserNotificationServiceImpl implements UserNotificationService{

    private final UserNotificationRepository userNotificationRepository;
    public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository){
        this.userNotificationRepository = userNotificationRepository;
    }
    @Override
    public boolean createUserNotification(List<User> listUser, Notification notification) {
        List<UserNotification> listUserNotification = new ArrayList<>();
        for(User user : listUser){
            UserNotification userNotification = new UserNotification();
            userNotification.setUser(user);
            userNotification.setNotification(notification);
            userNotification.setRead(false);
            listUserNotification.add(userNotification);
        }

        var save = userNotificationRepository.saveAll(listUserNotification);
        if(save == null){
            throw new CreateUserNotificationException(NotificationConstants.CREATE_NOTIFICATION_FAILED);
        }
        return true;
    }
}
