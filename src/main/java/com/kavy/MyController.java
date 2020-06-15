package com.kavy;

import com.kavy.client.ClientManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/websocket")
public class MyController {
    @Autowired
    private ClientManager clientManager;
    @GetMapping("/client")
    public String client() throws IOException {
        clientManager.sendTextMsg("你好");
        return "client";
    }
}
