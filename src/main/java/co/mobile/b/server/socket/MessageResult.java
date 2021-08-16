package co.mobile.b.server.socket;

import co.mobile.b.server.enums.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MessageResult {
    private String userKey;
    private int positionType;
    private String positionName;
    private String content;
    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonProperty("created_at_str")
    private String createdAtStr;

    public MessageResult(Message message) {
        this.userKey = message.getUserKey();
        this.positionType = message.getPositionType();
        this.positionName = Position.valueOf(positionType).getName();
        this.content = message.getContent();
        this.createdAt = message.getCreateAt();
        setChatCreatedAtForStr();
    }

    public MessageResult(AddMessageParam addMessageParam) {
        this.userKey = addMessageParam.getUserKey();
        this.positionType = addMessageParam.getPositionType();
        this.positionName = Position.valueOf(positionType).getName();
        this.content = addMessageParam.getContent();
        this.createdAtStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    private void setChatCreatedAtForStr() {
        this.createdAtStr = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
