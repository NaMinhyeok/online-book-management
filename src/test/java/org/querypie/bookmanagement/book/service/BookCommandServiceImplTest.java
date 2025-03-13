package org.querypie.bookmanagement.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.infrastructure.BookJpaRepository;
import org.querypie.bookmanagement.book.presentation.port.BookCommandService;
import org.querypie.bookmanagement.book.service.command.BookCreateCommand;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@Transactional
class BookCommandServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private BookCommandService bookCommandService;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @DisplayName("책을 등록한다")
    @Test
    void registerBook() {
        //given
        BookCreateCommand command = new BookCreateCommand("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");
        //when
        bookCommandService.registerBook(command);
        //then
        List<Book> books = bookJpaRepository.findAll();

        then(books).hasSize(1)
            .extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly(tuple("함께 자라기", "김창준", "인사이트", "9788966262335", "description", LocalDate.of(2018, 11, 30)));
    }

    @DisplayName("책의 정보를 수정한다")
    @Test
    void updateBook() {
        //given
        Book book = Book.builder()
            .title("함께 자라기")
            .author("김창준")
            .publisher("인사이트")
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2018-11-30")
            .build();
        bookJpaRepository.save(book);
        //when
        bookCommandService.updateBook(book.getId(), new BookUpdateCommand("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", null, "2017-12-11"));
        //then
        Book updatedBook = bookJpaRepository.findById(book.getId()).get();
        then(updatedBook).extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", "description", LocalDate.of(2017, 12, 11));
    }

    @DisplayName("책의 정보를 수정할 때 존재하지 않는 책을 수정하면 예외가 발생한다")
    @Test
    void updateBook_NotFound() {
        // given
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
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2017-12-11")
            .build();

        bookJpaRepository.saveAll(List.of(book1, book2));
        // when
        // then
        thenThrownBy(() -> bookCommandService.updateBook(0L, new BookUpdateCommand("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", null, "2017-12-11"))
        ).isEqualTo(CustomException.BOOK_NOT_FOUND);
    }

    @DisplayName("도서를 삭제한다")
    @Test
    void deleteBook() {
        //given
        Book book = Book.builder()
            .title("함께 자라기")
            .author("김창준")
            .publisher("인사이트")
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2018-11-30")
            .build();
        bookJpaRepository.save(book);
        //when
        bookCommandService.deleteBook(book.getId());
        //then
        List<Book> books = bookJpaRepository.findAll();
        then(books).isEmpty();
    }

    @DisplayName("도서를 삭제할 때 존재하지 않는 도서를 삭제하면 예외가 발생한다")
    @Test
    void deleteBook_NotFound() {
        // given
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
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2017-12-11")
            .build();

        bookJpaRepository.saveAll(List.of(book1, book2));
        // when
        // then
        thenThrownBy(() -> bookCommandService.deleteBook(0L))
            .isEqualTo(CustomException.BOOK_NOT_FOUND);
    }

}