package co.mobile.b.server.service;

import co.mobile.b.server.dto.request.AddRoomParam;
import co.mobile.b.server.dto.response.RoomCheckResult;
import co.mobile.b.server.dto.response.RoomResult;

public interface RoomService {
    RoomResult addRoom(AddRoomParam addRoomParam) throws Exception ;

    RoomResult getRoom(String userKey) throws Exception ;

    RoomCheckResult roomCheck(String inviteCode) throws Exception;
}
