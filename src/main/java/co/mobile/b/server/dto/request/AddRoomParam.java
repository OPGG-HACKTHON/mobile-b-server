package co.mobile.b.server.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class AddRoomParam {
    private String userKey;
}
