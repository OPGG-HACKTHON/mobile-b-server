package co.mobile.b.server.controller;

import co.mobile.b.server.dto.request.RoomCreateRequest;
import co.mobile.b.server.dto.response.RoomCreateResponse;
import co.mobile.b.server.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RoomCreateController {
    @Autowired
    private RoomService roomService;

    @MessageMapping("/create")
    @SendTo("/main")
    public RoomCreateResponse roomCreate(RoomCreateRequest request) throws Exception{

        // 방 코드
        String roomCode = roomService.getRandomRoomCode();
        return new RoomCreateResponse(request.getRoomName(),roomCode);
    }
}
