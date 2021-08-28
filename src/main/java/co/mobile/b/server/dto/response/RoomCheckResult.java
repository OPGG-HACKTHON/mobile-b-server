package co.mobile.b.server.dto.response;

import co.mobile.b.server.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomCheckResult {
    private String messageMapping;
    private String sendTo;
    private List<String> roomLog;

    public RoomCheckResult(Room room, String messageMapping, String sendTo, List<String> roomLog) {
        this.messageMapping = messageMapping;
        this.sendTo = sendTo + room.getInviteCode();
        this.roomLog = roomLog;
    }
}
