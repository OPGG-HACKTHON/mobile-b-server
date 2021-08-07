package co.mobile.b.server.socket;

import co.mobile.b.server.dto.request.RoomCreateRequest;
import co.mobile.b.server.dto.response.RoomCreateResponse;
import co.mobile.b.server.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SocketController {

    private final RoomService roomService;

    /**
     * Room create room create response.
     *
     * @param request  the request
     * @param accessor the accessor
     * @return the room create response
     * @throws Exception the exception
     */
    @MessageMapping("/create")
    @SendToUser("/queue/info")
    public RoomCreateResponse roomCreate(RoomCreateRequest request, SimpMessageHeaderAccessor accessor) throws Exception{

        //String sessionId = (String)accessor.getSessionAttributes().get(SESSION);
        // 방 코드
        String roomCode = roomService.getRandomRoomCode();
        log.info(roomCode);
        return new RoomCreateResponse(request.getRoomName(), roomCode);
    }
}
