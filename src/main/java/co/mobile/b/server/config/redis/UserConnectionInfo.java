package co.mobile.b.server.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserConnectionInfo {
    private String userName;
    private int positionType;
    private String inviteCode;
    private String userKey;
    private String sessionId;
    private Boolean isRoomHost;

    public UserConnectionInfo(String userName, int positionType, String inviteCode, String userKey, String sessionId) {
        this.userName = userName;
        this.positionType = positionType;
        this.inviteCode = inviteCode;
        this.userKey = userKey;
        this.sessionId = sessionId;
        this.isRoomHost = false;
    }
}
