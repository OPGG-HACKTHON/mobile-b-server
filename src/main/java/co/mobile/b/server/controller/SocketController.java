package co.mobile.b.server.controller;

import co.mobile.b.server.enums.Position;
import co.mobile.b.server.dto.request.AddMessageParam;
import co.mobile.b.server.dto.response.MessageResult;
import co.mobile.b.server.enums.MessageType;
import co.mobile.b.server.socket.UserConnectionInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

// TODO : 소켓관련 구조에 대한 고민
@Slf4j
@RequiredArgsConstructor
@Controller
public class SocketController {

    private final SimpMessageSendingOperations messageSendingOperations;
    private final StringRedisTemplate redisTemplate;
    ObjectMapper mapper = new ObjectMapper();

    @MessageMapping("/chat/send")
    // TODO : SimpMessagingTemplate 고려
    public void send(@RequestBody @Validated AddMessageParam addMessageParam){
        if (MessageType.LEAVE.equals((addMessageParam.getMessageType()))) {
            addMessageParam.setContent(addMessageParam.getUserKey() + "(" +
                    Position.valueOf(addMessageParam.getPositionType()).getName() + ")" + " 님이 퇴장하셨습니다.");
        }

        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();

        final String key = "room-" + addMessageParam.getDestRoomCode();

        MessageResult msgResult = new MessageResult(addMessageParam);
        try {
            String jsonString = mapper.writeValueAsString(msgResult);
            // redis 에 로그 저장
            stringStringListOperations.rightPush(key, jsonString);

            // message broadcast
            String broadcastURL = "/topic/message/" + addMessageParam.getDestRoomCode();
            messageSendingOperations.convertAndSend(broadcastURL, msgResult);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void enterBroadcast(String roomCode, String username, int positionType, String sessionId,String UUID){
        AddMessageParam broadCastMessage = new AddMessageParam();
        broadCastMessage.setMessageType(MessageType.JOIN);
        broadCastMessage.setDestRoomCode(roomCode);
        broadCastMessage.setUserKey(username);
        broadCastMessage.setPositionType(positionType);
        broadCastMessage.setContent(username + "(" + Position.valueOf(positionType).getName() + ") 님이 입장하셨습니다.");

        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        final HashOperations<String, String, String> stringStringHashOperations = redisTemplate.opsForHash();

        final String key = "room-" + roomCode;
        UserConnectionInfo connInfo = new UserConnectionInfo(username,positionType,roomCode,UUID);

        MessageResult msgResult = new MessageResult(broadCastMessage);
        try{
            String connInfoJsonString = mapper.writeValueAsString(connInfo);
            String enterLogJsonString = mapper.writeValueAsString(msgResult);

            // redis에 connection 저장
            stringStringHashOperations.put("connections",sessionId,connInfoJsonString);

            // redis 에 로그 저장
            stringStringListOperations.rightPush(key,enterLogJsonString);

            String broadcastURL = "/topic/message/" + roomCode;
            messageSendingOperations.convertAndSend(broadcastURL, msgResult);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void exitBroadcast(String sessionId){
        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        final HashOperations<String, String, String> stringStringHashOperations = redisTemplate.opsForHash();

        // 커넥션 정보 불러오기
        String connInfoJsonString = stringStringHashOperations.get("connections",sessionId);

        // 커넥션 정보 삭제
        stringStringHashOperations.delete("connections",sessionId);
        try {
            UserConnectionInfo connInfo = mapper.readValue(connInfoJsonString,UserConnectionInfo.class);

            final String key = "room-" + connInfo.getRoomCode();

            AddMessageParam broadCastMessage = new AddMessageParam();
            broadCastMessage.setMessageType(MessageType.LEAVE);
            broadCastMessage.setDestRoomCode(connInfo.getRoomCode());
            broadCastMessage.setUserKey(connInfo.getUserKey());
            broadCastMessage.setPositionType(connInfo.getPositionType());
            broadCastMessage.setContent(connInfo.getUserKey() + "(" + Position.valueOf(connInfo.getPositionType()).getName() + ") 님이 퇴장하셨습니다.");

            MessageResult msgResult = new MessageResult(broadCastMessage);
            String exitLogJsonString = mapper.writeValueAsString(msgResult);

            // redis에 퇴장 로그 기록
            stringStringListOperations.rightPush(key,exitLogJsonString);

            String broadcastURL = "/topic/message/" + connInfo.getRoomCode();
            messageSendingOperations.convertAndSend(broadcastURL, msgResult);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
