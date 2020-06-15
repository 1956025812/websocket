package com.kavy;

import com.kavy.client.ClientManager;
import com.kavy.client.ClientManagerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class MyConfiguration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myWebSocketHandler(),"/mywebsocket").setAllowedOrigins("*");
    }

    @Value("${webSocketUrl}")
    private String websocketUrl;

    @Bean
    public ClientManager clientManager() {
        return new ClientManagerImpl(websocketUrl);
    }

    @Bean
    MyWebSocketHandler myWebSocketHandler() {
        return new MyWebSocketHandler();
    }
}
