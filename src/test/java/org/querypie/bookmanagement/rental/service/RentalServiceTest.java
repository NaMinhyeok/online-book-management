package org.querypie.bookmanagement.rental.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.repository.BookRepository;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.rental.domain.Rental;
import org.querypie.bookmanagement.rental.repository.RentalRepository;
import org.querypie.bookmanagement.rental.service.command.RentalBookCommand;
import org.querypie.bookmanagement.support.IntegrationTestSupport;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@Transactional
class RentalServiceTest extends IntegrationTestSupport {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("책을 대여한다")
    @Test
    void rentalBookBooks() {
        //given
        Book book1 = Book.builder()
            .title("함께 자라기")
            .author("김창준")
            .publisher("인사이트")
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2018-11-30")
            .build();

        Book book2 = Book.builder()
            .title("프로그래머의 길")
            .author("로버트 C. 마틴")
            .publisher("인사이트")
            .isbn("9788966262336")
            .description("description")
            .publishedAt("2017-12-11")
            .build();

        bookRepository.saveAll(List.of(book1, book2));

        User user = User.builder()
            .name("나민혁")
            .email("nmh9097@gmail.com")
            .build();

        userRepository.save(user);
        //when
        LocalDateTime now = LocalDateTime.now();
        rentalService.rentalBooks(new RentalBookCommand(List.of(book1.getId(),book2.getId()), user.getId()), now);
        //then
        List<Rental> rentals = rentalRepository.findAll();

        then(rentals).hasSize(1)
            .extracting("rentalAt", "deadlineAt")
            .containsExactly(tuple(now, now.plusDays(7)));
        then(rentals.getFirst().getRentalBook())
            .extracting("book.title", "book.author", "book.publisher", "book.isbn")
            .containsExactly(
                tuple("함께 자라기", "김창준", "인사이트", "9788966262335"),
                tuple("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262336")
            );
    }

    @DisplayName("책을 대여할 때")
    @Nested
    class Context_rentalBook {
        @DisplayName("사용자가 존재하지 않으면")
        @Nested
        class whenUserNotFound {
            @DisplayName("사용자를 찾을 수 없다는 예외를 던진다")
            @Test
            void rentalBookUserNotFound() {
                //given
                Book book1 = Book.builder()
                    .title("함께 자라기")
                    .author("김창준")
                    .publisher("인사이트")
                    .isbn("9788966262335")
                    .description("description")
                    .publishedAt("2018-11-30")
                    .build();

                Book book2 = Book.builder()
                    .title("프로그래머의 길")
                    .author("로버트 C. 마틴")
                    .publisher("인사이트")
                    .isbn("9788966262336")
                    .description("description")
                    .publishedAt("2017-12-11")
                    .build();

                bookRepository.saveAll(List.of(book1, book2));

                User user = User.builder()
                    .name("나민혁")
                    .email("nmh9097@gmail.com")
                    .build();

                userRepository.save(user);
                //when
                LocalDateTime now = LocalDateTime.now();
                //then
                thenThrownBy(() -> rentalService.rentalBooks(new RentalBookCommand(List.of(book1.getId(),book2.getId()), 0L), now))
                    .isEqualTo(CustomException.USER_NOT_FOUND);
            }
        }

    }

}