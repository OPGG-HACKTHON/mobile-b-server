package co.mobile.b.server.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserConnectionInfo {
    private String userKey;
    private int positionType;
    private String roomCode;
    private String uuid;
    private Boolean isRoomHost;
}
