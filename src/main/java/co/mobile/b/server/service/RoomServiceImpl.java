package co.mobile.b.server.service;

import co.mobile.b.server.dto.request.AddRoomParam;
import co.mobile.b.server.dto.response.RoomCheckResult;
import co.mobile.b.server.dto.response.RoomResult;
import co.mobile.b.server.entity.Room;
import co.mobile.b.server.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final StringRedisTemplate redisTemplate;

    @Value("${app.domain}")
    private String appDomain;

    @Value("${stomp.message.mapping}")
    private String messageMapping;

    @Value("${stomp.send.to}")
    private String sendTo;

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

        return new RoomResult(roomRepository.save(new Room(addRoomParam)), appDomain);
    }

    @Override
    public RoomResult getRoom(String userKey) throws Exception {
        return new RoomResult(roomRepository.findByUserKeyAndDeletedFalse(userKey).orElseThrow(() -> new RuntimeException("해당 유저의 방이 존재 하지 않습니다.")), appDomain);
    }

    @Override
    public RoomCheckResult roomCheck(String inviteCode) throws Exception {
        Room room = roomRepository.findByInviteCodeAndAndDeletedFalse(inviteCode).orElseThrow(() -> new RuntimeException("초대코드가 유효하지 않습니다."));
        final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
        final String key = "room-" + inviteCode;

        final Long len = stringStringListOperations.size(key);
        final List<String> roomLog = stringStringListOperations.range(key,0, len);
        return new RoomCheckResult(room, messageMapping, sendTo, roomLog);
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
