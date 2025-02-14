package org.querypie.bookmanagement.rental.service;

import org.querypie.bookmanagement.rental.domain.RentalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalBookRepository extends JpaRepository<RentalBook, Long> {

    @Query("select rb from RentalBook rb where rb.book.id in :bookIds and rb.returnedAt is null")
    List<RentalBook> findRentedBooksByBookIdsAndReturnedAtIsNull(List<Long> bookIds);
}
