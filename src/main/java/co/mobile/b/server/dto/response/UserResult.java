package co.mobile.b.server.dto.response;

import co.mobile.b.server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserResult {
    private String email;
    private String nickname;

    public UserResult(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
