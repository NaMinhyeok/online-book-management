package org.querypie.bookmanagement.book.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.support.IntegrationTestSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.BDDAssertions.then;

@Transactional
class CustomBookJpaRepositoryImplTest extends IntegrationTestSupport {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @DisplayName("책을 이름 또는 저자로 검색한다")
    @Test
    void searchBooksByTitleOrAuthor() {
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

        Book book3 = Book.builder()
            .title("프로그래밍 책")
            .author("함께 저자")
            .publisher("인사이트")
            .isbn("9788966262337")
            .description("description")
            .publishedAt("2017-12-11")
            .build();

        bookJpaRepository.saveAll(List.of(book1, book2, book3));
        //when
        List<Book> results = bookJpaRepository.searchBooks("함께");
        //then
        then(results).hasSize(2)
            .extracting("title", "author")
            .containsExactly(
                tuple("함께 자라기", "김창준"),
                tuple("프로그래밍 책", "함께 저자"));

    }
}