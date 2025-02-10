package org.querypie.bookmanagement.book.controller.response;

import org.querypie.bookmanagement.book.domain.Book;

import java.util.List;

public record AllBooksResponseDto(
    List<BookResponseDto> books
) {
}
