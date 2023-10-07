package com.example.blog_springboot.modules.websocket.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.user.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketServiceImpl(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    @Transactional
    public void sendNotificationToClient(String sendTo, NotificationVm vm) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(vm);

        simpMessagingTemplate.convertAndSendToUser(sendTo, Constants.PRIVATE_CHANNEL_WEBSOCKET,message);
    }

    @Override
    @Transactional
    public void sendNotificationToClient(List<User> users, NotificationVm vm) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(vm);

        for(User u : users){
            simpMessagingTemplate.convertAndSendToUser(u.getUsername(), Constants.PRIVATE_CHANNEL_WEBSOCKET,message);
        }
    }

}
