package com.example.blog_springboot.modules.websocket.service;


import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WebSocketService {
    public void sendNotificationToClient(String sendTo, NotificationVm vm) throws JsonProcessingException;
    public void sendNotificationToClient(List<User> users, NotificationVm vm) throws JsonProcessingException;

}
