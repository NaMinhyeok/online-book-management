package org.querypie.bookmanagement.rental.service.command;

import java.util.List;

public record RentalBookCommand(
    List<Long> bookIds,
    Long userId
) {
}
