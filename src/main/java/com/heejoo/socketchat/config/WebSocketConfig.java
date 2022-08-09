package com.heejoo.socketchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/*
* @ServerEndPoint 를 붙인 클래스들은 WebSocket이 생성될 때마다 인스턴스 생성 및 JWA에 의해 관리
* --> 스프링의 @Autowired 가 설정도니 멤버들이 정상적으로 최적화되지 않음
* 즉, 이를 연결해주고 초기화해주는 클래스 필요
*/
@Component
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
