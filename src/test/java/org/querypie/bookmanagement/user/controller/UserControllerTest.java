package org.querypie.bookmanagement.user.controller;

import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.querypie.bookmanagement.user.controller.request.UserRegisterRequest;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestSupport {

    @Test
    void 사용자를_등록할_때_이름은_공백일_수_없다() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest("     ", "nmh9097@gmail.com");

        willDoNothing().given(userService).register(any(UserRegisterCommand.class));

        mockMvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 사용자를_등록할_때_이메일은_이메일_형식이어야_한다() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest("나민혁", "nmh9097gmail.com");

        willDoNothing().given(userService).register(any(UserRegisterCommand.class));

        mockMvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

}