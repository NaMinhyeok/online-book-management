package org.querypie.bookmanagement.book.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.ISBN;
import org.querypie.bookmanagement.book.service.command.BookCreateCommand;

public record BookCreateRequestDto(
    @NotBlank
    String title,
    @NotBlank
    String author,
    @NotBlank
    String publisher,
    @NotBlank
    @ISBN(type = ISBN.Type.ANY)
    String isbn,
    String description,
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    String publishedAt
) {
    public BookCreateCommand toCommand() {
        return new BookCreateCommand(title, author, publisher, isbn, description, publishedAt);
    }
}
