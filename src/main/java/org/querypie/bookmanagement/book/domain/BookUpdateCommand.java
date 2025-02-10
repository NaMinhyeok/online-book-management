package org.querypie.bookmanagement.book.domain;

public record BookUpdateCommand(
    String title,
    String author,
    String publisher,
    String isbn,
    String description,
    String publishedAt
) {
}
