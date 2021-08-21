package co.mobile.b.server.dto.response;

import co.mobile.b.server.entity.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomResult {
    // TODO : @JsonProperty로 다변경
    private Long roomSeq;
    private String userKey;
    private String inviteCode;
    private String inviteURL;
    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonProperty("createdAtStr")
    private String createdAtStr;

    public RoomResult(Room room, String appDomain) {
        this.roomSeq = room.getRoomSeq();
        this.userKey = room.getUserKey();
        this.inviteCode = room.getInviteCode();
        this.createdAt = room.getCreateAt();
        this.inviteURL = appDomain+inviteCode;
        setRoomCreatedAtForStr();
    }

    private void setRoomCreatedAtForStr() {
        this.createdAtStr = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
