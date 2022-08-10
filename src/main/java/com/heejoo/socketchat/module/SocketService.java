package com.heejoo.socketchat.module;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@ServerEndpoint(value = "/chat") // WebSocket 활성화시키는 매핑 정보 지정
@Slf4j
public class SocketService {
    private static final Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    /*클라이언트 접속, 메시지 수서니, 접속 해제에 따른 이벤트 핸들러, 어노테이션으로 메소드 정의*/
    // 클라이언트가 /chat 로 서버 접속 시 onOpen 메서드 실행 --> 클라이언트 정보를 매개변수인 Session 객체를 통해 전달받음
    // 이때 정적 필드인 clients 에 해당 Session 이 없으면, clients 에 접속된 클라이언트 추가함
    @OnOpen
    public void onOpen(Session s) {
        log.info("##### onOpen Session {} ", s.toString());
        if (!clients.contains(s)) {
            clients.add(s);
        } else {
            log.info("이미 연결된 Session - 정보 {}", s.toString());
        }
    }

    // 클라이언트로부터 메시지 전달 --> 이 클래스의 onMessage 메서드에 의해 clients 에 있는 모든 Session 에 메시지 전달
    @OnMessage
    public void onMessage(String msg, Session session) throws Exception {
        log.info("@@@@@ onMessage Message {} ", msg);
        JSONObject object = new JSONObject(msg);
        if (!object.getBoolean("isEnter")) {
            for (Session s : clients) {
                JSONObject tempObj = new JSONObject();
                // type : E(출입구) / M(메시지)
                tempObj.put("isEnter", true);
                tempObj.put("messageType", object.getString("messageType"));
                tempObj.put("nickname", object.getString("nickname"));
                tempObj.put("message", object.getString("nickname").concat("님이 입장하셨습니다."));
                tempObj.put("date", object.getString("date"));
                s.getBasicRemote().sendText(tempObj.toString());
            }
        } else {
            for (Session s : clients) {
                JSONObject tempObj = new JSONObject();
                // type : E(입장) / M(메시지)
                tempObj.put("isEnter", true);
                tempObj.put("messageType", object.getString("messageType"));
                tempObj.put("nickname", object.getString("nickname"));
                tempObj.put("message", object.getString("message"));
                tempObj.put("date", object.getString("date"));
                s.getBasicRemote().sendText(tempObj.toString());
            }
        }
    }

    // 클라이언트가 url을 바꾸거나 브라우저 종료 시, 자동으로 onClose() 메서드 실행 --> clients 에서 해당 클라이언트 정보 제거
    @OnClose
    public void onClose(Session s) {
        log.info("***** onClose Session {} ", s);
        clients.remove(s);
    }
}
