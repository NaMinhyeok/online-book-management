package org.querypie.bookmanagement.rental.service;

import java.util.List;

public record RentalBookCommand(
    List<String> booksIsbn,
    Long userId
) {
}
