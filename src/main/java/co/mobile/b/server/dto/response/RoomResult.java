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
    private Long roomSeq;
    private String userKey;
    private String inviteCode;
    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonProperty("created_at_str")
    private String createdAtStr;

    public RoomResult(Room room) {
        this.roomSeq = room.getRoomSeq();
        this.userKey = room.getUserKey();
        this.inviteCode = room.getInviteCode();
        this.createdAt = room.getCreateAt();
        setRoomCreatedAtForStr();
    }

    private void setRoomCreatedAtForStr() {
        this.createdAtStr = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
