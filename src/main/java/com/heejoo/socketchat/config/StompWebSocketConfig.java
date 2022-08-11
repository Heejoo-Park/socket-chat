package com.heejoo.socketchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/* 기본적으로 커넥션을 위한 STOMP Endpoint 설정 */
@Configuration
@EnableWebSocketMessageBroker // Stomp 사용하기 위해 선언
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("http://localhost:8088")
                .withSockJS();
    }

    /* 어플리케이션 내부에서 사용할 path 지정 가능 */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // setApplicationDestinationPrefixes : Client에서 send 요청 처리
        registry.setApplicationDestinationPrefixes("/pub");
        // 해당 경로로 SimpleBroker 등록
        // SimpleBroker는 해당하는 경로를 Subscribe 하는 Client에 메시지 전달하는 작업 수행
        registry.enableSimpleBroker("/sub");
        // enableStompBrokerRelay() : 외부 메신저 브로커(RabbitMQ, ActiveMQ 등)에 메시지 전달 기능
    }
}
