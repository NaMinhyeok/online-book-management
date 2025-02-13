package org.querypie.bookmanagement.rental.controller;

import org.querypie.bookmanagement.rental.service.RentalBookCommand;

import java.util.List;

public record RentalBookRequestDto(
    List<String> booksIsbn,
    Long userId
) {
    public RentalBookCommand toCommand() {
        return new RentalBookCommand(booksIsbn, userId);
    }
}
