package co.mobile.b.server.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UtilsControllerTest extends AbstractControllerTest {

    private final String BASE_URL = "http://localhost:8090/api/v1/utils";

    @DisplayName("포지션 리스트 조회")
    @Test
    @Order(1)
    void getPositionTest() throws Exception {
        mockMvc.perform(get(BASE_URL+"/position")
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .contentType(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document.document(
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("Http 상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("상태 설명 메시지"),
                                        fieldWithPath("responseTime").type(JsonFieldType.STRING).description("응답 시간"),

                                        fieldWithPath("result[].code").type(JsonFieldType.NUMBER).description("포지션 키"),
                                        fieldWithPath("result[].name").type(JsonFieldType.STRING).description("포지션 이름")
                                )
                        )
                );

    }

}