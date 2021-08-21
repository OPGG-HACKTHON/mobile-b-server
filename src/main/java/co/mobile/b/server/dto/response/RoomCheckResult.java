package co.mobile.b.server.dto.response;

import co.mobile.b.server.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomCheckResult {
    private String messageMapping;
    private String sendTo;

    public RoomCheckResult(Room room, String messageMapping, String sendTo) {
        this.messageMapping = messageMapping;
        this.sendTo = sendTo + room.getInviteCode();
    }
}
