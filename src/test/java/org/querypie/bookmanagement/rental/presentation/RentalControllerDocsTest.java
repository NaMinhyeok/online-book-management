package org.querypie.bookmanagement.rental.presentation;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.rental.presentation.request.RentalBookRequestDto;
import org.querypie.bookmanagement.rental.service.command.RentalBookCommand;
import org.querypie.bookmanagement.rental.service.command.ReturnBookCommand;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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

    @Test
    void 책을_반납한다() throws Exception {
        willDoNothing().given(rentalService).returnBooks(any(ReturnBookCommand.class), any(LocalDateTime.class));

        mockMvc.perform(post("/api/v1/rentals/{rentalId}/books/return", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RentalBookRequestDto(List.of(1L, 2L), 1L))
                ))
            .andExpect(status().isNoContent())
            .andDo(document("return-book",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 반납")
                        .tags("Rental")
                        .pathParameters(
                            parameterWithName("rentalId").description("대여 ID")
                        )
                        .requestFields(
                            fieldWithPath("bookIds").description("반납할 책 ID 목록"),
                            fieldWithPath("userId").description("반납자 ID")
                        )
                        .responseFields(
                            fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                            fieldWithPath("data").ignored(),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("returnBookResponse"))
                        .build()
                )));
    }

    @Test
    void 도서를_대여할_수_있는_상태인지_확인한다() throws Exception {
        long bookId = 1L;
        given(rentalService.isRentalAvailable(bookId)).willReturn(true);

        mockMvc.perform(get("/api/v1/rentals/books/{bookId}/rental-available", bookId))
            .andExpect(status().isOk())
            .andDo(document("is-rental-available",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("도서 대여 가능 여부 확인")
                        .tags("Rental")
                        .pathParameters(
                            parameterWithName("bookId").description("도서 ID")
                        )
                        .responseFields(
                            fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                            fieldWithPath("data.available").type(SimpleType.BOOLEAN).description("대여 가능 여부(true: 가능, false: 불가능)"),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("isRentalAvailableResponse"))
                        .build()
                )));
    }

}