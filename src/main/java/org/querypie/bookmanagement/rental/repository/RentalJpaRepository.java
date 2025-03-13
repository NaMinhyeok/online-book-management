package org.querypie.bookmanagement.rental.repository;

import org.querypie.bookmanagement.rental.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RentalJpaRepository extends JpaRepository<Rental, Long> {
    @Query("select distinct r from Rental r join fetch r.rentalBooks rb join fetch rb.book where r.id = :id")
    Optional<Rental> findWithBooksById(Long id);
}
