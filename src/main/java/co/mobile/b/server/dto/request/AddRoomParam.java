package co.mobile.b.server.dto.request;

import co.mobile.b.server.config.annotation.InviteCode;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AddRoomParam {
    @NotEmpty(message = "유저키를 입력하세요.")
    private String userKey;

    @InviteCode
    @Length(max = 10, message = "초대링크는 10자 이내여야 합니다.")
    private String inviteCode;
}
