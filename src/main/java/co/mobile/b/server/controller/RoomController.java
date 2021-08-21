package co.mobile.b.server.controller;

import co.mobile.b.server.dto.request.AddRoomParam;
import co.mobile.b.server.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;

    /**
     * 방 생성
     *
     * @param addRoomParam the add room param
     * @return the object
     * @throws Exception the exception
     */
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE, consumes = MediaTypes.HAL_JSON_VALUE)
    public Object addRoom(@RequestBody @Validated AddRoomParam addRoomParam) throws Exception{
        return roomService.addRoom(addRoomParam);
    }

    /**
     * 방 조회
     *
     * @param userKey the user key
     * @return the room
     * @throws Exception the exception
     */
    @GetMapping(value = "/{userKey}",produces = MediaTypes.HAL_JSON_VALUE)
    public Object getRoom(@PathVariable("userKey") String userKey) throws Exception {
        return roomService.getRoom(userKey);
    }

    /**
     * 방 확인
     *
     * @param inviteCode the invite code
     * @return the room
     * @throws Exception the exception
     */
    @GetMapping(value = "/check/{inviteCode}",produces = MediaTypes.HAL_JSON_VALUE)
    public Object roomCheck(@PathVariable("inviteCode") String inviteCode) throws Exception {
        return roomService.roomCheck(inviteCode);
    }
}
