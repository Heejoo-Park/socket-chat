package com.heejoo.socketchat.domain.stomp;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ChatRoomDTO {
    private String roomId;
    private String name;
    // WebSocketSession 은 Spring 에서 Websocket Connection 이 맺어진 세션
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDTO create(String name) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.roomId = UUID.randomUUID().toString();
        chatRoomDTO.name = name;
        return chatRoomDTO;
    }
}
