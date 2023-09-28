package com.example.blog_springboot.modules.websocket.controller;

import com.example.blog_springboot.modules.websocket.service.WebSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    private final WebSocketService webSocketService;

    public WebSocketController(WebSocketService webSocketService){
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/follow")
    public void sendFollowNotification(){
        //webSocketService.sendFollowNotification();
    }
}
