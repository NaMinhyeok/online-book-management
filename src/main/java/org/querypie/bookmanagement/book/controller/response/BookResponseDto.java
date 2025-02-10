package org.querypie.bookmanagement.book.controller.response;

import org.querypie.bookmanagement.book.domain.Book;

public record BookResponseDto(
    Long id,
    String title,
    String author,
    String publisher,
    String isbn,
    String description,
    String publishedAt
) {
    public static BookResponseDto of(Book book) {
        return new BookResponseDto(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPublisher(),
            book.getIsbn(),
            book.getDescription(),
            book.getFormattedPublishedAt()
        );
    }
}
