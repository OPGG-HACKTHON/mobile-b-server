package co.mobile.b.server.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    public String getRandomRoomCode(){
        int length = 7;
        boolean useLetters = true;
        boolean useNumbers = true;
        // 7자 난수 생성
        String roomCode = RandomStringUtils.random(length,useLetters,useNumbers);

        return roomCode;
    }
}
