package com.example.blog_springboot.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic","/user"); // message trả về nó sẽ gửi đi những client nào lắng nghe cái endpoint này
        registry.setApplicationDestinationPrefixes("/app"); // client to server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws/websocket")
                .setAllowedOrigins("*");

//        registry.addEndpoint("/ws/websocket")
//                .setAllowedOrigins("*")
//                .withSockJS();

        // phần này cho phép client truy cập vào websocket trong qua endpoint
    }
}
