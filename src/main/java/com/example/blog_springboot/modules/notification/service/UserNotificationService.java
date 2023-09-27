package com.example.blog_springboot.modules.notification.service;

import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.notification.model.Notification;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserNotificationService {
    public boolean createUserNotification(List<User> listUser, Notification notification);

    public SuccessResponse<List<NotificationVm>> getTop10NotificationCurrentUser(User user);

    public SuccessResponse<PagingResponse<List<NotificationVm>>> getNotificationCurrentUser(int pageIndex,User user);

    public SuccessResponse<Boolean> readNotification(int id, User user);
    public SuccessResponse<Boolean> readAllNotification(User user);
}
