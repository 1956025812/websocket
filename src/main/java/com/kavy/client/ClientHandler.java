package com.kavy.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
public class ClientHandler implements WebSocketHandler {

    private ClientManagerImpl clientManager;

    public ClientHandler(ClientManagerImpl clientManagerImpl) {
        this.clientManager = clientManagerImpl;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        log.info("afterConnectionEstablished: 连接建立成功>>>>，当前客户端ID为：{}, 客户端开启状态：{}", webSocketSession.getId(), webSocketSession.isOpen());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        log.info("handleMessage: 当前客户端ID为：{}，客户端开启状态：{}, 收到服务器消息:{}", webSocketSession.getId(), webSocketSession.isOpen(), webSocketMessage.toString());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        log.info("handleTransportError: 当前客户端ID为：{}，客户端开启状态：{}, 客户端通信端口出错, 异常原因为：{}", webSocketSession.getId(), webSocketSession.isOpen(), throwable.getMessage(), throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) {
        log.info("afterConnectionClosed: 当前客户端ID为：{}，客户端开启状态：{}, 关闭原因：{}", webSocketSession.getId(), webSocketSession.isOpen(), closeStatus.toString());
        clientManager.resetWebSocketSession();
    }

    @Override
    public boolean supportsPartialMessages() {
        log.info("supportsPartialMessages: 不支持分批发送");
        return false;
    }
}
