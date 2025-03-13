package org.querypie.bookmanagement.book.presentation.response;

import java.util.List;

public record AllBooksResponseDto(
    List<BookResponseDto> books
) {
}
