package co.mobile.b.server.config.stomp;

import co.mobile.b.server.config.redis.UserConnectionInfo;
import co.mobile.b.server.controller.SocketController;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class SocketEventHandler {

    private final SocketController socketController;

    /**
     * 커넥 리스너
     *
     * @param event the event
     * @throws Exception the exception
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) throws Exception{
        log.info("[ handleWebSocketConnectListener ]");
        socketController.enterBroadcast(getUserInfoBySession(event));
    }

    /**
     * 디스커넥 리스너
     *
     * @param event the event
     */
    @EventListener
    public void handleWebSocketDisConnectListener(SessionDisconnectEvent event) throws Exception {
        log.info("[ handleWebSocketDisConnectListener ]");
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

    /**
     * 헤더에서 유저정보 조회
     *
     * @param event the event
     * @return the user info by session
     * @throws Exception the exception
     */
    public UserConnectionInfo getUserInfoBySession(SessionConnectedEvent event) throws Exception{
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        GenericMessage msg = (GenericMessage) headerAccessor.getMessageHeaders().get("simpConnectMessage");
        Map<String, List> nativeHeaders = (Map<String, List>) msg.getHeaders().get("nativeHeaders");

        String inviteCode = (String) Optional.ofNullable(nativeHeaders.get("inviteCode"))
                .orElseThrow(() -> new RuntimeException("초대코드를 넣어주세요.")).get(0);
        String userName = (String) Optional.ofNullable(nativeHeaders.get("username"))
                .orElseThrow(() -> new RuntimeException("유저네임을 입력하세요.")).get(0);
        int positionType = Integer.parseInt((String) Optional.ofNullable(nativeHeaders.get("positionType"))
                .orElseThrow(() -> new RuntimeException("포지션 타입번호를 입력하세요.")).get(0));
        String userKey = (String) Optional.ofNullable(nativeHeaders.get("uuid"))
                .orElseThrow(() -> new RuntimeException("유저키를 입력하세요.")).get(0);
        String sessionId = headerAccessor.getSessionId();

        log.info("getUserInfo Session ID : [{}]", sessionId);

        return new UserConnectionInfo(userName, positionType, inviteCode, userKey, sessionId);
    }
}
