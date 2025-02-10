package org.querypie.bookmanagement.book.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.controller.request.BookCreateRequestDto;
import org.querypie.bookmanagement.book.controller.request.BookUpdateRequestDto;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.domain.BookCreateCommand;
import org.querypie.bookmanagement.book.domain.BookUpdateCommand;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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

    @Test
    void 책_목록을_조회한다() throws Exception {
        Book book1 = createBook(1L, "함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");
        Book book2 = createBook(2L, "프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", "description", "2018-11-30");

        given(bookService.getBooks()).willReturn(List.of(
            book1, book2
        ));

        mockMvc.perform(get("/api/v1/books"))
            .andExpect(status().isOk())
            .andDo(document("book-get",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 목록 조회")
                        .tags("Book")
                        .responseFields(
                            fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                            fieldWithPath("data.books[].id").description("책 목록").type(SimpleType.NUMBER).description("책 ID"),
                            fieldWithPath("data.books[].title").type(SimpleType.STRING).description("책 제목"),
                            fieldWithPath("data.books[].author").type(SimpleType.STRING).description("저자"),
                            fieldWithPath("data.books[].publisher").type(SimpleType.STRING).description("출판사"),
                            fieldWithPath("data.books[].isbn").type(SimpleType.STRING).description("ISBN"),
                            fieldWithPath("data.books[].description").type(SimpleType.STRING).description("설명").optional(),
                            fieldWithPath("data.books[].publishedAt").type(SimpleType.STRING).description("출판일"),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("bookGetResponse"))
                        .build()
                )));
    }

    @Test
    void 책을_단권_조회한다() throws Exception {
        Book book = createBook(1L, "함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");

        given(bookService.getBook(anyLong())).willReturn(book);

        mockMvc.perform(get("/api/v1/books/{bookId}", 1L))
            .andExpect(status().isOk())
            .andDo(document("book-get-one",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 단권 조회")
                        .tags("Book")
                        .responseFields(
                            fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                            fieldWithPath("data.id").type(SimpleType.NUMBER).description("책 ID"),
                            fieldWithPath("data.title").type(SimpleType.STRING).description("책 제목"),
                            fieldWithPath("data.author").type(SimpleType.STRING).description("저자"),
                            fieldWithPath("data.publisher").type(SimpleType.STRING).description("출판사"),
                            fieldWithPath("data.isbn").type(SimpleType.STRING).description("ISBN"),
                            fieldWithPath("data.description").type(SimpleType.STRING).description("설명").optional(),
                            fieldWithPath("data.publishedAt").type(SimpleType.STRING).description("출판일"),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("bookGetOneResponse"))
                        .build()
                )));

    }

    @Test
    void 책의_정보를_수정한다() throws Exception {
        BookUpdateRequestDto request = new BookUpdateRequestDto("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).updateBook(anyLong(), any(BookUpdateCommand.class));

        mockMvc.perform(put("/api/v1/books/{bookId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent())
            .andDo(document("book-update",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 수정")
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
                        .responseSchema(Schema.schema("bookUpdateResponse"))
                        .build()
                )));
    }

    private Book createBook(Long id, String title, String author, String publisher, String isbn, String description, String publishedAt) {
        Book book = Book.builder()
            .title(title)
            .author(author)
            .publisher(publisher)
            .isbn(isbn)
            .description(description)
            .publishedAt(publishedAt)
            .build();
        ReflectionTestUtils.setField(book, "id", id);

        return book;
    }
}