package co.mobile.b.server.controller;

import co.mobile.b.server.config.redis.UserConnectionInfo;
import co.mobile.b.server.dto.request.AddMessageParam;
import co.mobile.b.server.dto.response.MessageResult;
import co.mobile.b.server.enums.MessageType;
import co.mobile.b.server.enums.Position;
import co.mobile.b.server.service.RoomService;
import co.mobile.b.server.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SocketController {

    @Value("${stomp.send.to}")
    private String sendTo;

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final RoomService roomService;
    private final RedisUtil redisUtil;

    @MessageMapping("/chat/send")
    public void send(@RequestBody @Validated AddMessageParam addMessageParam) throws Exception {
        MessageResult msgResult = new MessageResult(addMessageParam);
        redisUtil.saveRoomLog(addMessageParam.getInviteCode(), msgResult);

        String broadcastURL = sendTo + addMessageParam.getInviteCode();
        simpMessageSendingOperations.convertAndSend(broadcastURL, msgResult);
    }

    public void enterBroadcast(UserConnectionInfo userConnectionInfo) throws Exception{
        String content = setMessage(MessageType.JOIN, userConnectionInfo.getUserName(), userConnectionInfo.getPositionType());
        userConnectionInfo.setIsRoomHost(roomService.isRoomHost(userConnectionInfo.getInviteCode(), userConnectionInfo.getUserKey()));
        MessageResult msgResult = new MessageResult(userConnectionInfo, MessageType.JOIN, content);

        redisUtil.saveConnections(userConnectionInfo);
        redisUtil.saveRoomLog(userConnectionInfo.getInviteCode(), msgResult);

        String broadcastURL = sendTo + userConnectionInfo.getInviteCode();
        simpMessageSendingOperations.convertAndSend(broadcastURL, msgResult);
    }

    public void exitBroadcast(String sessionId) throws Exception {
        UserConnectionInfo userConnectionInfo = redisUtil.getConnections(sessionId);
        redisUtil.deleteConnections(sessionId);

        String content = setMessage(MessageType.LEAVE, userConnectionInfo.getUserName(), userConnectionInfo.getPositionType());
        MessageResult msgResult = new MessageResult(userConnectionInfo, MessageType.LEAVE, content);
        redisUtil.saveRoomLog(userConnectionInfo.getInviteCode(), msgResult);

        String broadcastURL = sendTo + userConnectionInfo.getInviteCode();
        simpMessageSendingOperations.convertAndSend(broadcastURL, msgResult);
    }

    private String setMessage(MessageType messageType, String userName, int positionType){
        return userName + "(" + Position.valueOf(positionType).getName() + ") " + messageType.getMessage();
    }
}
