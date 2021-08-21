package co.mobile.b.server.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/docs")
@Controller
public class RestDocsController {

    @GetMapping(value = "/rooms", produces = MediaType.TEXT_HTML_VALUE)
    public String roomsDocs() {
        return "rooms-api-docs";
    }

    @GetMapping(value = "/utils", produces = MediaType.TEXT_HTML_VALUE)
    public String utilsDocs() {
        return "utils-api-docs";
    }
}
