package com.kavy.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 *
 */
@Slf4j
public class ClientManagerImpl implements ClientManager {
    private WebSocketSession webSocketSession;
    private ListenableFuture<WebSocketSession> webSocketSessionListenableFuture;
    private Boolean isConnected = false;
    private WebSocketClient standardWebSocketClient;
    private String websocketUrl;

    public ClientManagerImpl() {
        standardWebSocketClient = new StandardWebSocketClient();
    }

    public ClientManagerImpl(String websocketUrl) {
        this.websocketUrl = websocketUrl;
        standardWebSocketClient = new StandardWebSocketClient();
    }

    public synchronized WebSocketSession getSocketSession() {
        log.info("getSocketSession: isConnected: {}， 当前客户端ID：{}, 是否开启：{}", isConnected, null == webSocketSession ? null : webSocketSession.getId(),
                null == webSocketSession ? null : webSocketSession.isOpen());
        if (isConnected && webSocketSession.isOpen()) {
            return webSocketSession;
        }
        log.info("getSocketSession: 需要重新建立连接");
        return connectSocket();
    }


    private synchronized WebSocketSession connectSocket() {
        try {
            do {
                webSocketSessionListenableFuture = standardWebSocketClient.doHandshake(new ClientHandler(this), websocketUrl);
                webSocketSession = webSocketSessionListenableFuture.get();
                if (webSocketSession.isOpen()) {
                    log.info("connectSocket: 连接成功>>>>>>>， 当前新的客户端ID：{}， 开启状态：{}", webSocketSession.getId(), webSocketSession.isOpen());
                    isConnected = true;
                    return webSocketSession;
                } else {
                    Thread.sleep(3000);
                    log.info("connectSocket: 重新尝试连接>>>>>>>");
                }
            } while (!webSocketSession.isOpen());
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("connectSocket: 中断出错>>>>>>>, 当前客户端ID：{}, 是否开启：{}, 出错原因：{}", webSocketSession.getId(), webSocketSession.isOpen(), e.getMessage(), e);
        } catch (ExecutionException e) {
            log.error("connectSocket: 连接出错>>>>>>>, 当前客户端ID：{}, 是否开启：{}, 出错原因：{}", webSocketSession.getId(), webSocketSession.isOpen(), e.getMessage(), e);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            log.error("connectSocket: 尝试连接>>>>>>>, 当前客户端ID：{}, 是否开启：{}, 出错原因：{}", webSocketSession.getId(), webSocketSession.isOpen(), e.getMessage(), e);
            webSocketSessionListenableFuture.cancel(true);
            return connectSocket();
        }
        return null;
    }

    public void resetWebSocketSession() {
        isConnected = false;
        log.info("resetWebSocketSession: 连接断开>>>>重新连接, 当前客户端ID：{}, 是否开启：{}", webSocketSession.getId(), webSocketSession.isOpen());
        connectSocket();
    }

    @Override
    public void sendTextMsg(String msg) throws IOException {
        WebSocketSession socketSession = getSocketSession();
        log.info("sendTextMsg: 当前客户端ID：{}, 开启状态：{}, 发送信息：{}", socketSession.getId(), socketSession.isOpen(), msg);
        socketSession.sendMessage(new TextMessage(msg));
    }
}
