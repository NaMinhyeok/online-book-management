package org.querypie.bookmanagement.rental.domain;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

class RentalTest {

    @DisplayName("대여 신청시 대여 시간을 기록한다")
    @Test
    void registeredRentalDateTime() {
        //given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        //when
        Rental rental = Rental.create(List.of(createBook()), createUser(), registeredDateTime);
        //then
        BDDAssertions.then(rental.getRentalAt()).isEqualTo(registeredDateTime);
    }

    @DisplayName("대여 반납 기간은 대여 시간으로부터 7일 후이다")
    @Test
    void returnAtIsAfterRentalDatePlus7Days() {
        //given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        //when
        Rental rental = Rental.create(List.of(createBook()), createUser(), registeredDateTime);
        //then
        BDDAssertions.then(rental.getDeadlineAt()).isEqualTo(registeredDateTime.plusDays(7));
    }

    private User createUser() {
        return User.builder()
            .name("나민혁")
            .email("nmh9097@gmail.com")
            .build();
    }

    private Book createBook() {
        return Book.builder()
            .title("함께 자라기")
            .author("김창준")
            .publisher("인사이트")
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2018-11-30")
            .build();
    }

}