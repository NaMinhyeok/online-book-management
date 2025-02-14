package org.querypie.bookmanagement.rental.service.command;

import java.util.List;

public record ReturnBookCommand(
    Long rentalId,
    List<Long> bookIds,
    Long userId
) {
}
