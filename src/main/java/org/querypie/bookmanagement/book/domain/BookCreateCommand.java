package org.querypie.bookmanagement.book.domain;

public record BookCreateCommand(
    String title,
    String author,
    String publisher,
    String isbn,
    String description,
    String publishedAt
) {
}
