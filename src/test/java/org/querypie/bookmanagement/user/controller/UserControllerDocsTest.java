package org.querypie.bookmanagement.user.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.querypie.bookmanagement.user.controller.request.UserRegisterRequest;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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

}