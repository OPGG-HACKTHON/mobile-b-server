package co.mobile.b.server.socket;

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
    // TODO : message 를 프로퍼티로 다 뺄 것
    @NotEmpty(message = "userKey를 입력하세요.")
    private String userKey;
    @Min(value = 1, message = "포지션 타입 범위가 아닙니다.")
    @Max(value = 5, message = "포지션 타입 범위가 아닙니다.")
    private int positionType;
    @NotEmpty(message = "message를 입력하세요.")
    private String content;
    @NotNull(message = "messageType를 입력하세요.")
    private MessageType messageType;
}
