package co.mobile.b.server.service;

import co.mobile.b.server.dto.request.AddRoomParam;
import co.mobile.b.server.dto.response.RoomCheckResult;
import co.mobile.b.server.dto.response.RoomResult;
import co.mobile.b.server.entity.Room;
import co.mobile.b.server.repository.RoomRepository;
import co.mobile.b.server.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final RedisUtil redisUtil;

    @Value("${app.domain}")
    private String APP_DOMAIN;

    @Value("${stomp.message.mapping}")
    private String MESSAGE_MAPPING;

    @Value("${stomp.send.to}")
    private String SEND_TO;

    public RoomResult addRoom(AddRoomParam addRoomParam) throws Exception {
        if(roomRepository.existsByUserKeyAndAndDeletedIsFalse(addRoomParam.getUserKey())) {
            throw new RuntimeException("이미 방이 존재합니다.");
        }

        if(StringUtils.isEmpty(addRoomParam.getInviteCode())) {
            addRoomParam.setInviteCode(codeGenerator());
        } else {
            if(roomRepository.existsByInviteCodeAndDeletedIsFalse(addRoomParam.getInviteCode())) {
                throw new RuntimeException("이미 존재하는 초대 코드입니다.");
            }
        }

        return new RoomResult(roomRepository.save(new Room(addRoomParam)), APP_DOMAIN);
    }

    @Override
    public RoomResult getRoom(String userKey) throws Exception {
        return new RoomResult(roomRepository.findByUserKeyAndDeletedFalse(userKey).orElseThrow(() -> new RuntimeException("해당 유저의 방이 존재 하지 않습니다.")), APP_DOMAIN);
    }

    @Override
    public RoomCheckResult roomCheck(String inviteCode) throws Exception {
        Room room = roomRepository.findByInviteCodeAndAndDeletedFalse(inviteCode).orElseThrow(() -> new RuntimeException("초대코드가 유효하지 않습니다."));
        return new RoomCheckResult(room, MESSAGE_MAPPING, SEND_TO, redisUtil.getRoomLog(inviteCode));
    }

    @Override
    public Boolean isRoomHost(String inviteCode, String userKey) throws Exception {
        return roomRepository.existsByInviteCodeAndAndUserKeyAndDeletedIsFalse(inviteCode ,userKey);
    }

    @Transactional
    @Override
    public void delRoom(String inviteCode) throws Exception {
        Room room = roomRepository.findByInviteCodeAndAndDeletedFalse(inviteCode).orElseThrow(() -> new RuntimeException("존재하지 않는 초대 코드입니다."));
        room.delRoom();
    }

    private String codeGenerator() throws Exception {
        String inviteCode = "";
        do{
            // 난수 생성
            inviteCode = Integer.toString((int)(Math.random() * 10000));
            // 3자리 수 이하라면 0 추가
            while(inviteCode.length() < 4) inviteCode = "0" + inviteCode;
        }
        // 난수 초대 코드가 이미 존재하는 방 코드라면 재생성
        while (roomRepository.existsByInviteCodeAndDeletedIsFalse(inviteCode));
        return inviteCode;
    }
}
