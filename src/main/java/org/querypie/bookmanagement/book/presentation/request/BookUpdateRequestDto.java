package org.querypie.bookmanagement.book.presentation.request;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.ISBN;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;

public record BookUpdateRequestDto(
    String title,
    String author,
    String publisher,
    @ISBN(type = ISBN.Type.ANY)
    String isbn,
    String description,
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String publishedAt
) {
    public BookUpdateCommand toCommand() {
        return new BookUpdateCommand(title, author, publisher, isbn, description, publishedAt);
    }
}
