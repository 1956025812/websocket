package com.kavy.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

//@Component
public class ClientManagerImpl implements ClientManager {
    private WebSocketSession webSocketSession;
    private ListenableFuture<WebSocketSession> webSocketSessionListenableFuture;
    private Boolean isConnected = false;
    private WebSocketClient standardWebSocketClient;
//    @Value("${webSocketUrl}")
    private String websocketUrl;

    public ClientManagerImpl() {
        standardWebSocketClient = new StandardWebSocketClient();
    }
    public ClientManagerImpl(String websocketUrl) {
        this.websocketUrl = websocketUrl;
        standardWebSocketClient = new StandardWebSocketClient();
    }

    public WebSocketSession getSocketSession() {
        if (isConnected && webSocketSession.isOpen()) return webSocketSession;
        return connectSocket();
    }
    private WebSocketSession connectSocket() {
        try {
            do {
                webSocketSessionListenableFuture = standardWebSocketClient.doHandshake(new ClientHandler(this), websocketUrl);
                webSocketSession = webSocketSessionListenableFuture.get();
                if (webSocketSession.isOpen()) {
                    System.out.println("连接成功>>>>>>>");
                    isConnected = true;
                    return webSocketSession;
                } else {
                    Thread.sleep(3000);
                    System.out.println("重新尝试连接>>>>>>>");
                }
            } while (!webSocketSession.isOpen());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("中断出错>>>>>>>");
        } catch (ExecutionException e) {
            System.out.println("连接出错>>>>>>>");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("尝试连接>>>>>>>");
            webSocketSessionListenableFuture.cancel(true);//连接断开后需要取消之前的连接
            return connectSocket();
        }
        return null;
    }

    public void resetWebSocketSession() {
        isConnected = false;
        System.out.println("连接断开>>>>重新连接");
        connectSocket();
    }

    @Override
    public void sendTextMsg(String msg) throws IOException {
        getSocketSession().sendMessage(new TextMessage(msg));
    }
}
