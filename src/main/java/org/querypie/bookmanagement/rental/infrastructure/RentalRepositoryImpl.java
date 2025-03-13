package org.querypie.bookmanagement.rental.infrastructure;

import org.querypie.bookmanagement.rental.domain.Rental;
import org.querypie.bookmanagement.rental.service.port.RentalRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RentalRepositoryImpl implements RentalRepository {

    private final RentalJpaRepository rentalJpaRepository;

    public RentalRepositoryImpl(RentalJpaRepository rentalJpaRepository) {
        this.rentalJpaRepository = rentalJpaRepository;
    }

    @Override
    public void save(Rental rental) {
        rentalJpaRepository.save(rental);
    }

    @Override
    public Optional<Rental> findWithBooksById(Long id) {
        return rentalJpaRepository.findWithBooksById(id);
    }
}
