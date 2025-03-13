package org.querypie.bookmanagement.rental.repository;

import org.querypie.bookmanagement.rental.domain.RentalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentalBookJpaRepository extends JpaRepository<RentalBook, Long> {

    @Query("select rb from RentalBook rb where rb.book.id in :bookIds and rb.returnedAt is null")
    List<RentalBook> findRentedBooksByBookIdsAndReturnedAtIsNull(List<Long> bookIds);

    @Query("select rb from RentalBook rb where rb.book.id = :bookId and rb.returnedAt is null")
    Optional<RentalBook> findRentedBookByBookIdAndReturnedAtIsNull(Long bookId);

}
