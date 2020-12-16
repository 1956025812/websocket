package com.kavy.test;

import com.kavy.client.ClientManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
public class WebSocketTest {

    @Test
    public void socketTest() {
        ClientManagerImpl clientManagerImpl = new ClientManagerImpl("ws://localhost:8080/mywebsocket");
        try {
            WebSocketSession socketSession = null;
            while(true) {
                socketSession = clientManagerImpl.getSocketSession();
                socketSession.sendMessage(new TextMessage("abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc"));
                Thread.sleep(3000);
                log.info("发送消息: abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
