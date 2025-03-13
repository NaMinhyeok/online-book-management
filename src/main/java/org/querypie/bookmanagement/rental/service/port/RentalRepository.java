package org.querypie.bookmanagement.rental.service.port;

import org.querypie.bookmanagement.rental.domain.Rental;

import java.util.Optional;

public interface RentalRepository {
    void save(Rental rental);

    Optional<Rental> findWithBooksById(Long id);
}
