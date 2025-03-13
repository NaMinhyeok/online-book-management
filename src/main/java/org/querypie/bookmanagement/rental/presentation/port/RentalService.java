package org.querypie.bookmanagement.rental.presentation.port;

import org.querypie.bookmanagement.rental.service.command.RentalBookCommand;
import org.querypie.bookmanagement.rental.service.command.ReturnBookCommand;

import java.time.LocalDateTime;

public interface RentalService {
    void rentalBooks(RentalBookCommand command, LocalDateTime rentalAt);

    void returnBooks(ReturnBookCommand command, LocalDateTime returnAt);

    boolean isRentalAvailable(Long bookId);
}
