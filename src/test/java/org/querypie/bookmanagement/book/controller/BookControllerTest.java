package org.querypie.bookmanagement.book.controller;

import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.controller.request.BookCreateRequestDto;
import org.querypie.bookmanagement.book.controller.request.BookUpdateRequestDto;
import org.querypie.bookmanagement.book.service.command.BookCreateCommand;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;
import org.querypie.bookmanagement.support.ControllerTestSupport;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends ControllerTestSupport {

    @Test
    void 도서를_등록할_때_도서의_제목은_필수이다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto(null, "김창준", "인사이트", "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_공백_일_수_없다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto(" ", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_저자는_필수이다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", null, "인사이트", "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_저자는_공백_일_수_없다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", " ", "인사이트", "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_출판사는_필수이다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", "김창준", null, "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_출판사는_공백_일_수_없다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", "김창준", " ", "9788966262335", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_ISBN은_필수이다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", "김창준", "인사이트", null, "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_ISBN은_ISBN형식에_맞아야_한다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", "김창준", "인사이트", "qwertyuio", "description", "2018-11-30");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());

    }

    @Test
    void 도서를_등록할_때_도서의_출판일은_필수이다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", "김창준", "인사이트", "9788966262335", "description", null);

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서를_등록할_때_도서의_출판일은_yyyy_mm_dd_형식이어야_한다() throws Exception {
        BookCreateRequestDto request = new BookCreateRequestDto("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2025.01.01");

        willDoNothing().given(bookService).registerBook(any(BookCreateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서의_정보를_수정할_때_도서의_출판일은_yyyy_mm_dd_형식이어야_한다() throws Exception {
        BookUpdateRequestDto request = new BookUpdateRequestDto("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2025.01.01");

        willDoNothing().given(bookService).updateBook(anyLong(), any(BookUpdateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void 도서의_정보를_수정할_때_도서의_ISBN은_ISBN형식에_맞아야_한다() throws Exception {
        BookUpdateRequestDto request = new BookUpdateRequestDto("함께 자라기", "김창준", "인사이트", "qwertyuio", "description", "2018-11-30");

        willDoNothing().given(bookService).updateBook(anyLong(), any(BookUpdateCommand.class));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());

    }
}
