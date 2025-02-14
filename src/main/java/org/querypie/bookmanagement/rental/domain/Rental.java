package org.querypie.bookmanagement.rental.domain;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime rentalAt;
    private LocalDateTime deadlineAt;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalBook> rentalBook = new ArrayList<>();

    @Builder
    private Rental(List<Book> books, User user, LocalDateTime rentalAt) {
        this.rentalAt = rentalAt;
        this.deadlineAt = rentalAt.plusDays(7);
        this.user = user;
        this.rentalBook = books.stream()
            .map(book -> new RentalBook(book, this))
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
