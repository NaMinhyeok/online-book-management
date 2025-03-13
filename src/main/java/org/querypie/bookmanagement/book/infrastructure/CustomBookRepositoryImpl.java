package org.querypie.bookmanagement.book.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;

import java.util.List;

import static org.querypie.bookmanagement.book.domain.QBook.book;

@RequiredArgsConstructor
public class CustomBookRepositoryImpl implements CustomBookRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Book> searchBooks(@NonNull String keyword) {
        return queryFactory
            .selectFrom(book)
            .where(containsKeyword(keyword))
            .fetch();
    }

    private BooleanExpression containsKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return book.title.contains(keyword)
            .or(book.author.contains(keyword));
    }
}
