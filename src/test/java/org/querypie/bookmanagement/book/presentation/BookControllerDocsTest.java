package org.querypie.bookmanagement.book.presentation;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.epages.restdocs.apispec.SimpleType;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.presentation.request.BookCreateRequestDto;
import org.querypie.bookmanagement.book.presentation.request.BookUpdateRequestDto;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.service.command.BookCreateCommand;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
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

        Page<Book> booksPage = new PageImpl<>(List.of(book1, book2), PageRequest.of(0, 20, Sort.by("publishedAt").descending()), 2);

        given(bookService.getBooks(any(Pageable.class))).willReturn(booksPage);

        mockMvc.perform(get("/api/v1/books")
                .param("page", "1")
                .param("size", "20")
                .param("sort", "publishedAt,desc")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(document("book-get",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 목록 조회")
                        .tags("Book")
                        .queryParameters(
                            parameterWithName("page").type(SimpleType.NUMBER).description("페이지 번호(default: 1)"),
                            parameterWithName("size").type(SimpleType.NUMBER).description("페이지 크기(default: 20)"),
                            parameterWithName("sort").type(SimpleType.STRING).description("정렬 기준(default: publishedAt,desc) 사용 가능 값: publishedAt, title")
                        )
                        .responseFields(
                            fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                            fieldWithPath("data.content[].id").description("책 목록").type(SimpleType.NUMBER).description("책 ID"),
                            fieldWithPath("data.content[].title").type(SimpleType.STRING).description("책 제목"),
                            fieldWithPath("data.content[].author").type(SimpleType.STRING).description("저자"),
                            fieldWithPath("data.content[].publisher").type(SimpleType.STRING).description("출판사"),
                            fieldWithPath("data.content[].isbn").type(SimpleType.STRING).description("ISBN"),
                            fieldWithPath("data.content[].description").type(SimpleType.STRING).description("설명").optional(),
                            fieldWithPath("data.content[].publishedAt").type(SimpleType.STRING).description("출판일"),
                            fieldWithPath("data.pagination.currentPage").type(SimpleType.NUMBER).description("현재 페이지"),
                            fieldWithPath("data.pagination.totalPages").type(SimpleType.NUMBER).description("전체 페이지 수"),
                            fieldWithPath("data.pagination.pageSize").type(SimpleType.NUMBER).description("페이지 크기"),
                            fieldWithPath("data.pagination.totalElements").type(SimpleType.NUMBER).description("전체 요소 수"),
                            fieldWithPath("data.pagination.last").type(SimpleType.BOOLEAN).description("마지막 페이지 여부"),
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

    @Test
    void 책을_삭제한다() throws Exception {
        willDoNothing().given(bookService).deleteBook(anyLong());

        mockMvc.perform(delete("/api/v1/books/{bookId}", 1L))
            .andExpect(status().isNoContent())
            .andDo(document("book-delete",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 삭제")
                        .tags("Book")
                        .responseFields(
                            fieldWithPath("result").type(SimpleType.STRING).description("결과"),
                            fieldWithPath("data").ignored(),
                            fieldWithPath("error").ignored()
                        )
                        .responseSchema(Schema.schema("bookDeleteResponse"))
                        .build()
                )));
    }

    @Test
    void 책을_이름_또는_저자로_검색한다() throws Exception {
        Book book1 = createBook(1L, "함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");
        Book book2 = createBook(2L, "프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", "description", "2018-11-30");
        Book book3 = createBook(3L, "프로그래밍 책", "함께 저자", "인사이트", "9788966262337", "description", "2018-11-30");

        given(bookService.searchBooks("함께")).willReturn(List.of(
            book1, book3
        ));

        mockMvc.perform(get("/api/v1/books/search")
                .param("query", "함께"))
            .andExpect(status().isOk())
            .andDo(document("book-search",
                resource(
                    ResourceSnippetParameters.builder()
                        .description("책 검색")
                        .tags("Book")
                        .queryParameters(
                            parameterWithName("query").type(SimpleType.STRING).description("검색어")
                        )
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
                        .responseSchema(Schema.schema("bookSearchResponse"))
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