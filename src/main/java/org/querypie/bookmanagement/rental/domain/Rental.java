package org.querypie.bookmanagement.rental.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.common.domain.BaseEntity;
import org.querypie.bookmanagement.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Rental extends BaseEntity {

    private LocalDateTime rentalAt;
    private LocalDateTime returnAt;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalBook> rentalBook = new ArrayList<>();

    @Builder
    private Rental(List<Book> books, User user, LocalDateTime rentalAt) {
        this.rentalAt = rentalAt;
        this.returnAt = rentalAt.plusDays(7);
        this.rentalBook = books.stream()
            .map(book -> new RentalBook(book, this, user))
            .toList();
    }

    public static Rental create(List<Book> books, User user, LocalDateTime rentalAt) {
        return Rental.builder()
            .books(books)
            .user(user)
            .rentalAt(rentalAt)
            .build();
    }
}
