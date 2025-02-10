package org.querypie.bookmanagement.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.BDDAssertions.then;

class BookTest {

    @DisplayName("도서를 생성한다")
    @Test
    void createBook() {
        //given
        BookCreateCommand bookCreateCommand = new BookCreateCommand("함께 자라기", "김창준", "인사이트", "9788966262335", "description", "2018-11-30");
        //when
        Book book = Book.create(bookCreateCommand);
        //then
        then(book).extracting("title", "author", "publisher", "isbn", "description", "publishedAt")
            .containsExactly("함께 자라기", "김창준", "인사이트", "9788966262335", "description", LocalDate.of(2018, 11, 30));
    }

}