package org.querypie.bookmanagement.book.presentation.port;

import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.common.aop.Trace;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookQueryService {
    @Trace
    @Cacheable(value = "books", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Transactional(readOnly = true)
    Page<Book> getBooks(Pageable pageable);

    void verifySortProperties(Pageable pageable);

    @Transactional(readOnly = true)
    Book getBook(Long bookId);

    @Cacheable(value = "books", key = "#keyword")
    @Transactional(readOnly = true)
    List<Book> searchBooks(String keyword);
}
