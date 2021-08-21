package co.mobile.b.server.controller;

import co.mobile.b.server.dto.response.PositionResult;
import co.mobile.b.server.enums.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/utils")
public class UtilsController {

    /**
     * 포지션 리스트
     *
     * @return the position
     * @throws Exception the exception
     */
    @GetMapping(value = "/position", produces = MediaTypes.HAL_JSON_VALUE)
    public Object getPosition() throws Exception {
        return Arrays.stream(Position.values())
                .map(PositionResult::new)
                .collect(Collectors.toList());
    }
}
