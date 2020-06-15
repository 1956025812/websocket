package com.kavy;

import org.springframework.web.socket.*;

public class MyWebSocketHandler implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        System.out.println("连接建立成功>>>>");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        System.out.println("收到客户端消息" + webSocketMessage.toString());
        webSocketSession.sendMessage(new TextMessage("服务器返回>>>>"));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("客户端通信端口出错");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("客户端连接关闭");
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("5555555");
        return false;
    }
}
