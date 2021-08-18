package co.mobile.b.server.service;

import co.mobile.b.server.dto.request.AddRoomParam;
import co.mobile.b.server.dto.response.RoomResult;

public interface RoomService {
    RoomResult addRoom(AddRoomParam addRoomParam);
    Boolean checkRoomExistsByInviteCode(String inviteCode);
}
