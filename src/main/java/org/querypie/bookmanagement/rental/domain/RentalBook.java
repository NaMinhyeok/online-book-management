package org.querypie.bookmanagement.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.common.domain.BaseEntity;

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

    private LocalDateTime returnedAt;

    @Builder
    private RentalBook(Book book, Rental rental) {
        this.book = book;
        this.rental = rental;
    }

    public void returnBook(LocalDateTime returnAt) {
        this.returnedAt = returnAt;
    }

    public boolean isReturned() {
        return returnedAt != null;
    }

    public boolean isRenting() {
        return returnedAt == null;
    }
}
