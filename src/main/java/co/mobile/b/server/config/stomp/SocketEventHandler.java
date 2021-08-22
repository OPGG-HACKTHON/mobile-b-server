package co.mobile.b.server.config.stomp;

import co.mobile.b.server.socket.SocketController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class SocketEventHandler {
    private final SocketController socketController;

    /**
     * 커넥 리스너
     *
     * @param event the event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        GenericMessage msg = (GenericMessage) headerAccessor.getMessageHeaders().get("simpConnectMessage");
        Map<String, List> nativeHeaders = (Map<String, List>) msg.getHeaders().get("nativeHeaders");

        log.info("Received a new web socket connection. Session ID : [{}]", headerAccessor.getSessionId());

        // 헤더로부터 초대코드, 유저명, 포지션 저장
        String inviteCode = (String)nativeHeaders.get("inviteCode").get(0);
        String username = (String) nativeHeaders.get("username").get(0);
        int positionType = Integer.parseInt((String)nativeHeaders.get("positionType").get(0));
        String sessionId = headerAccessor.getSessionId();

        socketController.enterBroadcast(inviteCode,username,positionType,sessionId);
    }


    /**
     * 디스커넥 리스너
     *
     * @param event the event
     */
    @EventListener
    public void handleWebSocketDisConnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("Web socket session closed. Session ID : [{}]", headerAccessor.getSessionId());

        socketController.exitBroadcast(headerAccessor.getSessionId());
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
