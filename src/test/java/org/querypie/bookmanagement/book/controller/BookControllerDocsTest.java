package org.querypie.bookmanagement.book.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.controller.request.BookCreateRequestDto;
import org.querypie.bookmanagement.book.domain.BookCreateCommand;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.springframework.http.MediaType;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerDocsTest extends ControllerTestSupport {

    @Test
    void 책을_등록한다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(document("book-create",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 등록")
                        .tags("Book")
                        .requestFields(
                            fieldWithPath("title").description("책 제목"),
                            fieldWithPath("author").description("저자"),
                            fieldWithPath("publisher").description("출판사"),
                            fieldWithPath("isbn").description("ISBN"),
                            fieldWithPath("description").description("설명"),
                            fieldWithPath("publishedAt").description("출판일")
                        )
                        .responseFields(
                            fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                            fieldWithPath("data").ignored(),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("bookCreateResponse"))
                        .build()
                )));
    }
}