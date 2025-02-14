package org.querypie.bookmanagement.rental.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

class RentalBookTest {

    @DisplayName("도서가 대여되었는지 확인한다")
    @Test
    void isRenting() {
        //given
        RentalBook rentalBook = RentalBook.builder()
            .build();
        //when
        boolean sut = rentalBook.isRenting();
        //then
        then(sut).isTrue();
    }

    @DisplayName("도서가 반납되었는지 확인한다")
    @Test
    void isReturned() {
        //given
        RentalBook rentalBook = RentalBook.builder()
            .build();
        ReflectionTestUtils.setField(rentalBook, "returnedAt", LocalDateTime.of(2025, 1, 1, 1, 1));
        //when
        boolean sut = rentalBook.isReturned();
        //then
        then(sut).isTrue();
    }

    @DisplayName("도서를 반납한다")
    @Test
    void returnBook() {
        //given
        RentalBook rentalBook = RentalBook.builder()
            .build();
        //when
        rentalBook.returnBook(LocalDateTime.of(2025, 1, 1, 1, 1));
        //then
        then(rentalBook).extracting("returnedAt").isEqualTo(LocalDateTime.of(2025, 1, 1, 1, 1));
    }

}