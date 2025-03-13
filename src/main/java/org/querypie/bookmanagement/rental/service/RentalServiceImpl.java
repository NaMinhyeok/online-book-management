package org.querypie.bookmanagement.rental.service;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.service.port.BookRepository;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.rental.domain.Rental;
import org.querypie.bookmanagement.rental.domain.RentalBook;
import org.querypie.bookmanagement.rental.presentation.port.RentalService;
import org.querypie.bookmanagement.rental.service.command.RentalBookCommand;
import org.querypie.bookmanagement.rental.service.command.ReturnBookCommand;
import org.querypie.bookmanagement.rental.service.port.RentalBookRepository;
import org.querypie.bookmanagement.rental.service.port.RentalRepository;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.service.port.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RentalBookRepository rentalBookRepository;

    @Transactional
    @Override
    public void rentalBooks(RentalBookCommand command, LocalDateTime rentalAt) {
        verifyRentalAvailable(command.bookIds());
        List<Book> books = findBooksBy(command.bookIds());
        User user = userRepository.findById(command.userId())
            .orElseThrow(() -> CustomException.USER_NOT_FOUND);
        Rental rental = Rental.create(books, user, rentalAt);
        rentalRepository.save(rental);
    }

    private void verifyRentalAvailable(List<Long> bookIds) {
        List<RentalBook> rentalBooks = rentalBookRepository.findRentedBooksByBookIdsAndReturnedAtIsNull(bookIds);
        if (!rentalBooks.isEmpty()) {
            throw CustomException.RENTAL_BOOKS_ALREADY_RENTED;
        }
    }

    private List<Book> findBooksBy(List<Long> bookIds) {
        List<Book> books = bookRepository.findAllById(bookIds);
        if (books.size() != bookIds.size()) {
            throw CustomException.BOOK_NOT_FOUND;
        }
        Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, book -> book));

        List<Book> duplicateBooks = bookIds.stream()
            .map(bookMap::get)
            .collect(Collectors.toList());
        return duplicateBooks;
    }

    @CacheEvict(value = "rentalAvailable", allEntries = true)
    @Transactional
    @Override
    public void returnBooks(ReturnBookCommand command, LocalDateTime returnAt) {
        Rental rental = rentalRepository.findWithBooksById(command.rentalId())
            .orElseThrow(() -> CustomException.RENTAL_NOT_FOUND);
        rental.returnBooks(command.userId(), command.bookIds(), returnAt);
    }

    @Cacheable(value = "rentalAvailable", key = "#bookId")
    @Transactional(readOnly = true)
    @Override
    public boolean isRentalAvailable(Long bookId) {
        return rentalBookRepository.findRentedBookByBookIdAndReturnedAtIsNull(bookId).isEmpty();
    }
}
