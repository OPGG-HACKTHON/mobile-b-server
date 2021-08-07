package co.mobile.b.server.controller;

import co.mobile.b.server.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
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
}
