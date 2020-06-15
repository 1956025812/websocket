package com.kavy.client;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class ClientHandler implements WebSocketHandler {

    private ClientManagerImpl clientManager;

    public ClientHandler(ClientManagerImpl clientManagerImpl) {
        this.clientManager = clientManagerImpl;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        System.out.println("连接建立成功>>>>");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        System.out.println("收到服务器消息" + webSocketMessage.toString());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("客户端通信端口出错");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("客户端连接关闭");
        clientManager.resetWebSocketSession();
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("5555555");
        return false;
    }
}
