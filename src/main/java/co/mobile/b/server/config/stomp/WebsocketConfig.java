package co.mobile.b.server.config.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    
    // TODO : JWT 사용시 채널 인터셉터사용
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // in-memory message-broker, topic에 대한 prefix 설정
        config.enableSimpleBroker("/topic","/queue");

        // 메세지를 수신하는 handler의 메세지 prefix 설정
        config.setApplicationDestinationPrefixes("/stream");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/socket").addInterceptors(new HttpHandshakeInterceptor())/*.setAllowedOrigins("*")*/.withSockJS();
    }
}
