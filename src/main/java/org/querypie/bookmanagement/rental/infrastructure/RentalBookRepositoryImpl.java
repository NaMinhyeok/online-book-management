package org.querypie.bookmanagement.rental.infrastructure;

import org.querypie.bookmanagement.rental.domain.RentalBook;
import org.querypie.bookmanagement.rental.service.port.RentalBookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RentalBookRepositoryImpl implements RentalBookRepository {

    private final RentalBookJpaRepository rentalBookJpaRepository;

    public RentalBookRepositoryImpl(RentalBookJpaRepository rentalBookJpaRepository) {
        this.rentalBookJpaRepository = rentalBookJpaRepository;
    }

    @Override
    public Optional<RentalBook> findRentedBookByBookIdAndReturnedAtIsNull(Long bookId) {
        return rentalBookJpaRepository.findRentedBookByBookIdAndReturnedAtIsNull(bookId);
    }

    @Override
    public List<RentalBook> findRentedBooksByBookIdsAndReturnedAtIsNull(List<Long> bookIds) {
        return rentalBookJpaRepository.findRentedBooksByBookIdsAndReturnedAtIsNull(bookIds);
    }
}
