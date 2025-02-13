package org.querypie.bookmanagement.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.common.domain.BaseEntity;
import org.querypie.bookmanagement.user.domain.User;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public RentalBook(Book book, Rental rental, User user) {
        this.book = book;
        this.rental = rental;
        this.user = user;
    }
}
