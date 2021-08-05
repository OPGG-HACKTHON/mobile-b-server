package co.mobile.b.server.controller;

import co.mobile.b.server.dto.request.RoomCreateRequest;
import co.mobile.b.server.dto.response.RoomCreateResponse;
import co.mobile.b.server.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import static org.springframework.messaging.simp.stomp.StompHeaders.SESSION;

@Controller
public class RoomCreateController {
    @Autowired
    private RoomService roomService;

    @MessageMapping("/create")
    @SendToUser("/queue/info")
    public RoomCreateResponse roomCreate(RoomCreateRequest request, SimpMessageHeaderAccessor accessor) throws Exception{

        //String sessionId = (String)accessor.getSessionAttributes().get(SESSION);
        // 방 코드
        String roomCode = roomService.getRandomRoomCode();
        return new RoomCreateResponse(request.getRoomName(),roomCode);
    }
}
