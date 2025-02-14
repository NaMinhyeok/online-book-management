package org.querypie.bookmanagement.rental.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.common.domain.BaseEntity;
import org.querypie.bookmanagement.user.domain.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalBook extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    private LocalDateTime returnAt;

    public RentalBook(Book book, Rental rental) {
        this.book = book;
        this.rental = rental;
    }

    public void returnBook(LocalDateTime returnAt) {
        this.returnAt = returnAt;
    }
}
