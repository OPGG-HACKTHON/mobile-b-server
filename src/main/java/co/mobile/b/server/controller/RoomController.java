package co.mobile.b.server.controller;

import co.mobile.b.server.dto.request.RoomCreateRequest;
import co.mobile.b.server.dto.response.RoomCreateResponse;
import co.mobile.b.server.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;


    /**
     * 단일 방 조회
     *
     * @param seq          the seq
     * @param loginUserSeq the login user seq
     * @return the room
     * @throws Exception the exception
     */
    @GetMapping(value = "/{seq}",produces = MediaTypes.HAL_JSON_VALUE)
    public Object getRoom(@PathVariable("seq") Long seq, @RequestAttribute("seq") Long loginUserSeq) throws Exception {
        return "getRoom";
    }

    /**
     * 룸 전체 리스트 검색
     *
     * TODO : @ModelAttribute @Validated RoomListParam 생성
     * @return the room list
     * @throws Exception the exception
     */
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public Object getRoomList() throws Exception {
        return "getRoomList";
    }

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
        return new RoomCreateResponse(request.getRoomName(),roomCode);
    }
}
