package com.kavy.client;

import java.io.IOException;

public interface ClientManager {
    void sendTextMsg(String msg) throws IOException;
}
