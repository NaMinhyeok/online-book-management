package org.querypie.bookmanagement.rental.service.port;

import org.querypie.bookmanagement.rental.domain.RentalBook;

import java.util.List;
import java.util.Optional;

public interface RentalBookRepository {
    Optional<RentalBook> findRentedBookByBookIdAndReturnedAtIsNull(Long bookId);

    List<RentalBook> findRentedBooksByBookIdsAndReturnedAtIsNull(List<Long> bookIds);
}
