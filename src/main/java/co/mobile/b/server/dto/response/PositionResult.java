package co.mobile.b.server.dto.response;

import co.mobile.b.server.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PositionResult {
    private Integer code;
    private String name;

    public PositionResult(Position position) {
        this.code = position.getType();
        this.name = position.getName();
    }
}
