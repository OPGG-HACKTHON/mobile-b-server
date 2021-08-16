package co.mobile.b.server.socket;

import co.mobile.b.server.enums.Position;
import co.mobile.b.server.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @MessageMapping("/chat/send")
    // TODO : SimpMessagingTemplate 고려
//    @SendTo("/topic/message")

    public void send(@RequestBody @Validated AddMessageParam addMessageParam) throws Exception {
        // TODO : DB? Cache? RabbitMQ 또는 sqlite 사용해서 본인 device에만?
        if (MessageType.JOIN.equals(addMessageParam.getMessageType())) {
            addMessageParam.setContent(Position.valueOf(addMessageParam.getPositionType()).getName() + "님이 입장하셨습니다.");
        }
        messageSendingOperations.convertAndSend("/topic/message", new MessageResult(addMessageParam));
    }
}
