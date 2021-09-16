package co.mobile.b.server.dto.request;

import co.mobile.b.server.enums.MessageType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AddMessageParam {
    @NotEmpty(message = "userKey를 입력하세요.")
    private String userKey;
    @Min(value = 1, message = "포지션 타입 범위가 아닙니다.")
    @Max(value = 5, message = "포지션 타입 범위가 아닙니다.")
    private int positionType;
    @NotEmpty(message = "message를 입력하세요.")
    private String content;
    @NotNull(message = "messageType를 입력하세요.")
    private MessageType messageType;
    @NotEmpty(message = "초대코드를 입력하세요.")
    private String inviteCode;
}
