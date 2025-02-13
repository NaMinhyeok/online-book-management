package org.querypie.bookmanagement.rental.repository;

import org.querypie.bookmanagement.rental.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
