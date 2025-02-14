package org.querypie.bookmanagement.rental.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.rental.controller.request.RentalBookRequestDto;
import org.querypie.bookmanagement.rental.service.command.RentalBookCommand;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RentalControllerDocsTest extends ControllerTestSupport {

    @Test
    void 책을_대여한다() throws Exception {
        RentalBookRequestDto request = new RentalBookRequestDto(List.of(1L, 2L), 1L);

        willDoNothing().given(rentalService).rentalBooks(any(RentalBookCommand.class), any(LocalDateTime.class));
        mockMvc.perform(
                post("/api/v1/rentals")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andDo(
                document("rental-book",
                    resource(
                        ResourceSnippetParameters.builder()
                            .description("책 대여")
                            .tags("Rental")
                            .requestFields(
                                fieldWithPath("bookIds").description("대여할 책 ID 목록"),
                                fieldWithPath("userId").description("대여자 ID")
                            )
                            .responseFields(
                                fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                                fieldWithPath("data").ignored(),
                                fieldWithPath("error").ignored()
                            )
                            .responseSchema(Schema.schema("rentalBookResponse"))
                            .build()
                    )
                )
            );
    }


}