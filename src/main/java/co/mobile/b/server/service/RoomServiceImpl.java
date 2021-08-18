package co.mobile.b.server.service;

import co.mobile.b.server.dto.request.AddRoomParam;
import co.mobile.b.server.dto.response.RoomCheckResult;
import co.mobile.b.server.dto.response.RoomResult;
import co.mobile.b.server.entity.Room;
import co.mobile.b.server.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;

    @Value("${app.domain}")
    private String appDomain;

    @Value("{message.mapping}")
    private String messageMapping;

    @Value("{send.to}")
    private String sendTo;

    public RoomResult addRoom(AddRoomParam addRoomParam) throws Exception {
        if (roomRepository.existsByUserKeyAndAndDeletedIsFalse(addRoomParam.getUserKey())) {
            throw new RuntimeException("이미 방이 존재합니다.");
        }
        return new RoomResult(roomRepository.save(new Room(addRoomParam, appDomain)));
    }

    @Override
    public RoomCheckResult roomCheck(String inviteCode) throws Exception {
        Room room = roomRepository.findByInviteCodeAndAndDeletedFalse(inviteCode).orElseThrow(() -> new RuntimeException("초대코드가 유효하지 않습니다."));
        return new RoomCheckResult(room, messageMapping, sendTo);
    }
}
