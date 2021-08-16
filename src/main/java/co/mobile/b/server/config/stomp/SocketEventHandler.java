package co.mobile.b.server.config.stomp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SocketEventHandler {

    /**
     * 커넥 리스너
     *
     * @param event the event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //GenericMessage msg = (GenericMessage) headerAccessor.getMessageHeaders().get("simpConnectMessage");

        log.info("handleWebSocketConnectListener. Session ID : [{}]", headerAccessor.getSessionId());
    }


    /**
     * 디스커넥 리스너
     *
     * @param event the event
     */
    @EventListener
    public void handleWebSocketDisConnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("handleWebSocketDisConnectListener. Session ID : [{}]", headerAccessor.getSessionId());
    }

    /**
     * 헤더 생성
     *
     * @param sessionId the session id
     * @return the message headers
     */
    public MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        return headerAccessor.getMessageHeaders();
    }
}
