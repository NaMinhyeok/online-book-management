package org.querypie.bookmanagement.user.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.querypie.bookmanagement.user.controller.request.UserRegisterRequest;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerDocsTest extends ControllerTestSupport {

    @Test
    void 사용자를_등록한다() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest("나민혁", "nmh9097@gmail.com");

        willDoNothing().given(userService).register(any(UserRegisterCommand.class));

        mockMvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(document("user-register",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("사용자 등록")
                        .tags("User")
                        .requestFields(
                            fieldWithPath("name").type(SimpleType.STRING).description("사용자 이름"),
                            fieldWithPath("email").type(SimpleType.STRING).description("이메일")
                        )
                        .responseFields(
                            fieldWithPath("result").description("결과"),
                            fieldWithPath("data").ignored(),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("userRegisterResponse"))
                        .build()
                )
            ));
    }

    @Test
    void 모든_사용자를_조회한다() throws Exception {
        User user1 = createUser(1L, "나민혁", "nmh9097@gmail.com");
        User user2 = createUser(2L, "홍길동", "gildong@naver.com");

        given(userService.getUsers()).willReturn(List.of(user1,user2));

        mockMvc.perform(get("/api/v1/users"))
            .andExpect(status().isOk())
            .andDo(document("user-get-all",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("모든 사용자 조회")
                        .tags("User")
                        .responseFields(
                            fieldWithPath("result").description("결과"),
                            fieldWithPath("data.users").description("사용자 목록"),
                            fieldWithPath("data.users[].id").type(SimpleType.NUMBER).description("사용자 ID"),
                            fieldWithPath("data.users[].name").type(SimpleType.STRING).description("사용자 이름"),
                            fieldWithPath("data.users[].email").type(SimpleType.STRING).description("이메일"),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("userGetAllResponse"))
                        .build()
                )
            ));
    }

    private User createUser(Long id, String name, String email) {
        User user = User.builder()
            .name(name)
            .email(email)
            .build();
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

}