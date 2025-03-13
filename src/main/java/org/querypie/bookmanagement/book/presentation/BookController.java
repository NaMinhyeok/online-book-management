package org.querypie.bookmanagement.book.presentation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.presentation.port.BookCommandService;
import org.querypie.bookmanagement.book.presentation.port.BookQueryService;
import org.querypie.bookmanagement.book.presentation.request.BookCreateRequestDto;
import org.querypie.bookmanagement.book.presentation.request.BookUpdateRequestDto;
import org.querypie.bookmanagement.book.presentation.response.AllBooksResponseDto;
import org.querypie.bookmanagement.book.presentation.response.BookResponseDto;
import org.querypie.bookmanagement.common.support.response.ApiResponse;
import org.querypie.bookmanagement.common.support.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookQueryService bookQueryService;
    private final BookCommandService bookCommandService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<?> registerBook(
        @Valid @RequestBody BookCreateRequestDto request
    ) {
        bookCommandService.registerBook(request.toCommand());
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<PageResponse<BookResponseDto>> getBooks(
        @PageableDefault(size = 20, sort = "publishedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Book> books = bookQueryService.getBooks(pageable);
        return ApiResponse.success(PageResponse.of(books.map(BookResponseDto::of)));
    }

    @GetMapping("/{bookId}")
    public ApiResponse<BookResponseDto> getBook(
        @PathVariable Long bookId
    ) {
        Book book = bookQueryService.getBook(bookId);
        return ApiResponse.success(BookResponseDto.of(book));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{bookId}")
    public ApiResponse<?> updateBook(
        @PathVariable Long bookId,
        @Valid @RequestBody BookUpdateRequestDto request
    ) {
        bookCommandService.updateBook(bookId, request.toCommand());
        return ApiResponse.success();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{bookId}")
    public ApiResponse<?> deleteBook(
        @PathVariable Long bookId
    ) {
        bookCommandService.deleteBook(bookId);
        return ApiResponse.success();
    }

    @GetMapping("/search")
    public ApiResponse<AllBooksResponseDto> searchBooks(
        @NotBlank @RequestParam String query
    ) {
        List<Book> books = bookQueryService.searchBooks(query);
        return ApiResponse.success(new AllBooksResponseDto(books.stream().map(BookResponseDto::of).toList()));
    }

}
