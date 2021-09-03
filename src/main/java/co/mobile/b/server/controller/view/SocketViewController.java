package co.mobile.b.server.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/socket")
@RequiredArgsConstructor
@Controller
public class SocketViewController {

    @GetMapping(value = "/test", produces = MediaType.TEXT_HTML_VALUE)
    public String test() {
        return "test";
    }
}
