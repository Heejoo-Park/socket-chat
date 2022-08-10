package com.heejoo.socketchat.module.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    @GetMapping("/webSocket/chat")
    public String chat() {
        return "/websocket/chat";
    }

}
