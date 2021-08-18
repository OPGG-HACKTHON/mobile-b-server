package co.mobile.b.server.service;

import co.mobile.b.server.dto.request.AddRoomParam;
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
    private String domain;

    public RoomResult addRoom(AddRoomParam addRoomParam) {
        if (roomRepository.existsByUserKeyAndAndDeletedIsFalse(addRoomParam.getUserKey())) {
            throw new RuntimeException("이미 방이 존재합니다.");
        }

        String inviteCode = "";
        do{
            // 난수 생성
            inviteCode = Integer.toString((int)(Math.random() * 10000));
            // 3자리 수 이하라면 0 추가
            while(inviteCode.length() < 4) inviteCode = "0" + inviteCode;
        }
        // 난수 초대 코드가 이미 존재하는 방 코드라면 재생성
        while (roomRepository.existsByInviteCodeAndDeletedIsFalse(inviteCode));

        return new RoomResult(roomRepository.save(new Room(addRoomParam, inviteCode)));
    }

    public Boolean checkRoomExistsByInviteCode(String inviteCode){
        return roomRepository.existsByInviteCodeAndDeletedIsFalse(inviteCode);
    }
}
