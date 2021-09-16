package co.mobile.b.server.util;

import co.mobile.b.server.config.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 에러 Response 처리 부분 */
public class ResponseUtil {

    public static void writeJson(HttpServletResponse httpServletResponse, ObjectMapper objectMapper, ErrorResponse errorResponse) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
