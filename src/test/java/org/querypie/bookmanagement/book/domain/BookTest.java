package org.querypie.bookmanagement.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.service.command.BookCreateCommand;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;

import java.time.LocalDate;

import static org.assertj.core.api.BDDAssertions.then;

class BookTest {

    @DisplayName("도서를 생성한다")
    @Test
    void createBook() {
        //given
        //when
        Book book = Book.create("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");
        //then
        then(book).extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly("함께 자라기", "김창준", "인사이트", "9788966262335", "description", LocalDate.of(2018, 11, 30));
    }

    @DisplayName("도서의 정보를 수정한다")
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
        //when
        book.update("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262334", "description", "2018-11-30");
        //then
        then(book).extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly("프로그래머의 길", "로버트 C. 마틴", "인사이트", "9788966262334", "description", LocalDate.of(2018, 11, 30));
    }

    @DisplayName("도서의 정보를 수정 할 때 값이 없으면 수정하지 않는다")
    @Test
    void updateBookWhen() {
        //given
        Book book = Book.builder()
            .title("함께 자라기")
            .author("김창준")
            .publisher("인사이트")
            .isbn("9788966262335")
            .description("description")
            .publishedAt("2018-11-30")
            .build();
        //when
        book.update("프로그래머의 길", null, null, null, null, null);
        //then
        then(book).extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly("프로그래머의 길", "김창준", "인사이트", "9788966262335", "description", LocalDate.of(2018, 11, 30));
    }
}