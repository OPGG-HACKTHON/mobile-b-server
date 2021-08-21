package co.mobile.b.server.socket;

import co.mobile.b.server.enums.Position;
import co.mobile.b.server.service.RoomServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.json.JSONObject;
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

    private final RoomServiceImpl roomService;
    private final SimpMessageSendingOperations messageSendingOperations;
    private final StringRedisTemplate redisTemplate;
    ObjectMapper mapper = new ObjectMapper();
    JSONObject jsonObject = new JSONObject();

    @MessageMapping("/chat/send")
    // TODO : SimpMessagingTemplate 고려
    public void send(@RequestBody @Validated AddMessageParam addMessageParam) throws Exception {
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

    public void enterBroadcast(String roomCode, String username, int positionType){
        AddMessageParam broadCastMessage = new AddMessageParam();
        broadCastMessage.setMessageType(MessageType.JOIN);
        broadCastMessage.setDestRoomCode(roomCode);
        broadCastMessage.setUserKey(username);
        broadCastMessage.setPositionType(positionType);
        broadCastMessage.setContent(username + "(" + Position.valueOf(positionType).getName() + ") 님이 입장하셨습니다.");

        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();

        final String key = "room-" + roomCode;

        MessageResult msgResult = new MessageResult(broadCastMessage);
        try{
            String jsonString = mapper.writeValueAsString(msgResult);
            // redis 에 로그 저장
            stringStringListOperations.rightPush(key,jsonString);

            String broadcastURL = "/topic/message/" + roomCode;
            messageSendingOperations.convertAndSend(broadcastURL, msgResult);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
