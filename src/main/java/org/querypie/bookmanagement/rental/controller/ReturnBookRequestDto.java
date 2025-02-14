package org.querypie.bookmanagement.rental.controller;

import org.querypie.bookmanagement.rental.service.command.ReturnBookCommand;

import java.util.List;

public record ReturnBookRequestDto(
    List<Long> bookIds,
    Long userId
) {
    public ReturnBookCommand toCommand(Long rentalId) {
        return new ReturnBookCommand(rentalId, bookIds, userId);
    }
}
