package org.querypie.bookmanagement.book.service.command;

public record BookUpdateCommand(
    String title,
    String author,
    String publisher,
    String isbn,
    String description,
    String publishedAt
) {
}
