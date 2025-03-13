package org.querypie.bookmanagement.book.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.domain.BookSortField;
import org.querypie.bookmanagement.book.presentation.port.BookQueryService;
import org.querypie.bookmanagement.book.service.port.BookRepository;
import org.querypie.bookmanagement.common.aop.Trace;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookQueryServiceImpl implements BookQueryService {

    private final BookRepository bookRepository;

    @Trace
    @Cacheable(value = "books", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Transactional(readOnly = true)
    @Override
    public Page<Book> getBooks(Pageable pageable) {
        verifySortProperties(pageable);
        return bookRepository.findAll(pageable);
    }

    @Override
    public void verifySortProperties(Pageable pageable) {
        pageable.getSort().forEach(order -> BookSortField.validateSortField(order.getProperty()));
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> CustomException.BOOK_NOT_FOUND);
    }

    @Cacheable(value = "books", key = "#keyword")
    @Transactional(readOnly = true)
    @Override
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }
}
