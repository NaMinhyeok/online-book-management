package org.querypie.bookmanagement.rental.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.*;

class RentalTest {

    @DisplayName("대여 신청시 대여 시간을 기록한다")
    @Test
    void registeredRentalDateTime() {
        //given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        //when
        Rental rental = Rental.create(List.of(createBook()), createUser(), registeredDateTime);
        //then
        then(rental.getRentalAt()).isEqualTo(registeredDateTime);
    }

    @DisplayName("대여 반납 기간은 대여 시간으로부터 7일 후이다")
    @Test
    void returnAtIsAfterRentalDatePlus7Days() {
        //given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        //when
        Rental rental = Rental.create(List.of(createBook()), createUser(), registeredDateTime);
        //then
        then(rental.getDeadlineAt()).isEqualTo(registeredDateTime.plusDays(7));
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

    private User createUserById(Long id) {
        User user = User.builder()
            .name("나민혁")
            .email("nmh9097@gmail.com")
            .build();
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    private Book createBookById(Long id) {
        Book book = Book.builder()
            .title("함께 자라기")
            .author("김창준")
            .publisher("인사이트")
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2018-11-30")
            .build();
        ReflectionTestUtils.setField(book, "id", id);
        return book;
    }

    @DisplayName("returnBooks 메서드는")
    @Nested
    class Describe_returnBooks {

        @DisplayName("대여한 사용자가 책을 반납하면")
        @Nested
        class Context_returnBooks {
            @DisplayName("대여한 책이 반납되었는지 확인한다")
            @Test
            void returnBooks() {
                //given
                LocalDateTime returnAt = LocalDateTime.of(2025, 1, 1, 0, 0);
                Rental rental = Rental.builder()
                    .books(List.of(createBookById(1L)))
                    .user(createUserById(1L))
                    .rentalAt(returnAt.minusDays(3))
                    .build();                //when
                rental.returnBooks(1L, List.of(1L), LocalDateTime.now());
                //then
                then(rental.getRentalBooks().getFirst().isReturned()).isTrue();
            }
        }

        @DisplayName("대여한 사용자가 책을 반납하면")
        @Nested
        class Context_returnNotRentingBooks {
            @DisplayName("해당 책이 대여내역에 존재하지 않으면 예외를 던진다")
            @Test
            void returnBooks() {
                //given
                LocalDateTime returnAt = LocalDateTime.of(2025, 1, 1, 0, 0);
                Rental rental = Rental.builder()
                    .books(List.of(createBookById(1L), createBookById(2L), createBookById(3L)))
                    .user(createUserById(1L))
                    .rentalAt(returnAt.minusDays(3))
                    .build();
                //when
                //then
                thenThrownBy(() -> rental.returnBooks(1L, List.of(1L, 2L, 4L), returnAt))
                    .isEqualTo(CustomException.RENTAL_BOOK_NOT_FOUND);
            }

            @DisplayName("대여한 책의 일부만 반납할 수 있다")
            @Test
            void returnPortionBooks() {
                //given
                LocalDateTime returnAt = LocalDateTime.of(2025, 1, 1, 0, 0);
                Rental rental = Rental.builder()
                    .books(List.of(createBookById(1L), createBookById(2L), createBookById(3L)))
                    .user(createUserById(1L))
                    .rentalAt(returnAt.minusDays(3))
                    .build();
                //when
                rental.returnBooks(1L, List.of(1L, 2L), returnAt);
                //then
                then(rental.getRentalBooks())
                    .extracting("book.id", "returnedAt")
                    .containsExactly(
                        tuple(1L, LocalDateTime.of(2025, 1, 1, 0, 0)),
                        tuple(2L, LocalDateTime.of(2025, 1, 1, 0, 0)),
                        tuple(3L, null)
                    );
            }
        }

        @DisplayName("대여한 사용자가 아닌 사용자가 대여한 책을 반납하려고 시도하면")
        @Nested
        class Context_returnBooksByOtherUser {
            @DisplayName("사용자가 일치하지 않는 예외를 던진다")
            @Test
            void returnBooks() {
                //given
                LocalDateTime returnAt = LocalDateTime.of(2025, 1, 1, 0, 0);
                Rental rental = Rental.builder()
                    .books(List.of(createBookById(1L), createBookById(2L), createBookById(3L)))
                    .user(createUserById(1L))
                    .rentalAt(returnAt.minusDays(3))
                    .build();
                //when
                //then
                thenThrownBy(() -> rental.returnBooks(2L, List.of(1L, 2L), returnAt))
                    .isEqualTo(CustomException.RENTAL_USER_NOT_MATCHED);
            }
        }
    }

}