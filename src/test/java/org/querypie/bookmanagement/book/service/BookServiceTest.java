package org.querypie.bookmanagement.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.domain.BookCreateCommand;
import org.querypie.bookmanagement.book.domain.BookUpdateCommand;
import org.querypie.bookmanagement.book.repository.BookRepository;
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
class BookServiceTest extends IntegrationTestSupport {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("책을 등록한다")
    @Test
    void registerBook() {
        //given
        BookCreateCommand command = new BookCreateCommand("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");
        //when
        bookService.registerBook(command);
        //then
        List<Book> books = bookRepository.findAll();

        then(books).hasSize(1)
            .extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly(tuple("함께 자라기", "김창준", "인사이트", "9788966262335", "description", LocalDate.of(2018, 11, 30)));
    }

    @DisplayName("책을 모두 조회한다")
    @Test
    void getBooks() {
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
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2017-12-11")
            .build();

        bookRepository.saveAll(List.of(book1, book2));
        //when
        List<Book> books = bookService.getBooks();
        //then
        then(books).hasSize(2)
            .extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly(
                tuple("함께 자라기", "김창준", "인사이트", "9788966262335", "description", LocalDate.of(2018, 11, 30)),
                tuple("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", "description", LocalDate.of(2017, 12, 11))
            );
    }

    @DisplayName("책을 조회한다")
    @Test
    void getBook() {
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

        bookRepository.saveAll(List.of(book1, book2));
        // when
        Book book = bookService.getBook(book1.getId());
        // then
        then(book).extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly("함께 자라기", "김창준", "인사이트", "9788966262335", "description", LocalDate.of(2018, 11, 30));
    }

    @DisplayName("책을 조회할 때 존재하지 않는 책을 조회하면 예외가 발생한다")
    @Test
    void getBook_NotFound() {
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

        bookRepository.saveAll(List.of(book1, book2));
        // when
        // then
        thenThrownBy(() -> bookService.getBook(0L))
            .isEqualTo(CustomException.BOOK_NOT_FOUND);
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
        bookRepository.save(book);
        //when
        bookService.updateBook(book.getId(), new BookUpdateCommand("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", null, "2017-12-11"));
        //then
        Book updatedBook = bookRepository.findById(book.getId()).get();
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

        bookRepository.saveAll(List.of(book1, book2));
        // when
        // then
        thenThrownBy(() -> bookService.updateBook(0L, new BookUpdateCommand("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262335", null, "2017-12-11"))
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
        bookRepository.save(book);
        //when
        bookService.deleteBook(book.getId());
        //then
        List<Book> books = bookRepository.findAll();
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

        bookRepository.saveAll(List.of(book1, book2));
        // when
        // then
        thenThrownBy(() -> bookService.deleteBook(0L))
            .isEqualTo(CustomException.BOOK_NOT_FOUND);
    }

}