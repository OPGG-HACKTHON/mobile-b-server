package co.mobile.b.server.controller;

import co.mobile.b.server.dto.request.AddRoomParam;
import co.mobile.b.server.entity.Room;
import co.mobile.b.server.repository.RoomRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoomControllerTest extends AbstractControllerTest {

    private final String BASE_URL = "http://localhost:8090/api/v1/rooms";

    @Autowired
    private RoomRepository roomRepository;

    @DisplayName("방 생성")
    @Test
    @Order(1)
    public void addRoomTest() throws Exception {

        AddRoomParam param = AddRoomParam.builder()
                .userKey("userKey")
                .inviteCode("inviteCode")
                .build();

        mockMvc.perform(post(BASE_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(param)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("userKey").type(JsonFieldType.STRING).description("유저 고유 번호"),
                                fieldWithPath("inviteCode").type(JsonFieldType.STRING).description("초대 링크 또는 코드(생략 시 자동생성)")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("Http 상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("상태 설명 메시지"),
                                fieldWithPath("responseTime").type(JsonFieldType.STRING).description("응답 시간"),

                                fieldWithPath("result.roomSeq").type(JsonFieldType.NUMBER).description("방 키"),
                                fieldWithPath("result.userKey").type(JsonFieldType.STRING).description("유저 키"),
                                fieldWithPath("result.inviteCode").type(JsonFieldType.STRING).description("초대 코드"),
                                fieldWithPath("result.inviteURL").type(JsonFieldType.STRING).description("초대 URL"),
                                fieldWithPath("result.createdAtStr").type(JsonFieldType.STRING).description("생성 시간")
                        )
                ));

    }

    @DisplayName("방 조회")
    @Test
    @Order(2)
    void getRoomTest() throws Exception {
        mockMvc.perform(get(BASE_URL+"/{userKey}", "userKey")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document.document(
                                pathParameters(
                                        parameterWithName("userKey").description("유저 키")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("Http 상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("상태 설명 메시지"),
                                        fieldWithPath("responseTime").type(JsonFieldType.STRING).description("응답 시간"),

                                        fieldWithPath("result.roomSeq").type(JsonFieldType.NUMBER).description("방 키"),
                                        fieldWithPath("result.userKey").type(JsonFieldType.STRING).description("유저 키"),
                                        fieldWithPath("result.inviteCode").type(JsonFieldType.STRING).description("초대 코드"),
                                        fieldWithPath("result.inviteURL").type(JsonFieldType.STRING).description("초대 URL"),
                                        fieldWithPath("result.createdAtStr").type(JsonFieldType.STRING).description("생성 시간")
                                )
                        )
                );

    }

    @DisplayName("방 확인")
    @Test
    @Order(3)
    void roomCheckTest() throws Exception {

        Room room = roomRepository.findByUserKeyAndDeletedFalse("userKey").orElseThrow(() -> new RuntimeException("방이 존재 하지 않습니다."));

        mockMvc.perform(get(BASE_URL+"/check/{inviteCode}", room.getInviteCode())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document.document(
                                pathParameters(
                                        parameterWithName("inviteCode").description("초대 코드")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("Http 상태 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("상태 설명 메시지"),
                                        fieldWithPath("responseTime").type(JsonFieldType.STRING).description("응답 시간"),

                                        fieldWithPath("result.messageMapping").type(JsonFieldType.STRING).description("메세지 URL"),
                                        fieldWithPath("result.sendTo").type(JsonFieldType.STRING).description("구독 URL"),
                                        fieldWithPath("result.roomLog[]").type(JsonFieldType.ARRAY).description("방 이력")
                                )
                        )
                );

    }
}