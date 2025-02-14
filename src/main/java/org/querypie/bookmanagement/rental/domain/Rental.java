package org.querypie.bookmanagement.rental.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.common.domain.BaseEntity;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Rental extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime rentalAt;
    private LocalDateTime deadlineAt;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalBook> rentalBooks = new ArrayList<>();

    @Builder
    private Rental(List<Book> books, User user, LocalDateTime rentalAt) {
        this.rentalAt = rentalAt;
        this.deadlineAt = rentalAt.plusDays(7);
        this.user = user;
        this.rentalBooks = books.stream()
            .map(book -> RentalBook.builder()
                .book(book)
                .rental(this)
                .build())
            .toList();
    }

    public static Rental create(List<Book> books, User user, LocalDateTime rentalAt) {
        return Rental.builder()
            .books(books)
            .user(user)
            .rentalAt(rentalAt)
            .build();
    }

    public void returnBooks(Long userId, List<Long> bookIds, LocalDateTime returnAt) {
        verifyUserBy(userId);
        List<RentalBook> candidateReturnBooks = getCandidateReturnBooks(bookIds);
        candidateReturnBooks.forEach(rentalBook -> rentalBook.returnBook(returnAt));
    }

    private void verifyUserBy(Long userId) {
        if (!userId.equals(user.getId())) {
            throw CustomException.RENTAL_USER_NOT_MATCHED;
        }
    }

    private List<RentalBook> getCandidateReturnBooks(List<Long> bookIds) {
        List<RentalBook> candidateReturnBooks = rentalBooks.stream()
            .filter(rentalBook -> bookIds.contains(rentalBook.getBook().getId()))
            .filter(RentalBook::isRenting)
            .toList();

        if (candidateReturnBooks.size() != bookIds.size()) {
            throw CustomException.RENTAL_BOOK_NOT_FOUND;
        }
        return candidateReturnBooks;
    }
}
