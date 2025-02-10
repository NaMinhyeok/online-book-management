package org.querypie.bookmanagement.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.domain.BookCreateCommand;
import org.querypie.bookmanagement.book.repository.BookRepository;
import org.querypie.bookmanagement.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.BDDAssertions.then;

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

}