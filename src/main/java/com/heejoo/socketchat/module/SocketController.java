package com.heejoo.socketchat.module;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocketController {

    @GetMapping("/chat")
    public String chat() {
        return "/chat";
    }

}
