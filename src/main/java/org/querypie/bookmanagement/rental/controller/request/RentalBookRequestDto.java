package org.querypie.bookmanagement.rental.controller.request;

import org.querypie.bookmanagement.rental.service.command.RentalBookCommand;

import java.util.List;

public record RentalBookRequestDto(
    List<Long> bookIds,
    Long userId
) {
    public RentalBookCommand toCommand() {
        return new RentalBookCommand(bookIds, userId);
    }
}
