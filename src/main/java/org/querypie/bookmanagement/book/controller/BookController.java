package org.querypie.bookmanagement.book.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.book.controller.request.BookCreateRequestDto;
import org.querypie.bookmanagement.book.controller.response.AllBooksResponseDto;
import org.querypie.bookmanagement.book.controller.response.BookResponseDto;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.service.BookService;
import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<?> registerBook(
        @Valid @RequestBody BookCreateRequestDto request
    ) {
        bookService.registerBook(request.toCommand());
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<AllBooksResponseDto> getBooks() {
        List<Book> books = bookService.getBooks();
        return ApiResponse.success(new AllBooksResponseDto(books.stream().map(BookResponseDto::of).toList()));
    }

}
