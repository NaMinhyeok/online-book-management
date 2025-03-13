package org.querypie.bookmanagement.book.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.domain.BookSortField;
import org.querypie.bookmanagement.book.service.command.BookCreateCommand;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;
import org.querypie.bookmanagement.book.service.port.BookRepository;
import org.querypie.bookmanagement.common.aop.Trace;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @CacheEvict(value = "books", allEntries = true)
    public void registerBook(BookCreateCommand command) {
        bookRepository.save(Book.create(command.title(), command.author(), command.publisher(), command.isbn(), command.description(), command.publishedAt()));
    }

    @Trace
    @Cacheable(value = "books", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Transactional(readOnly = true)
    public Page<Book> getBooks(Pageable pageable) {
        verifySortProperties(pageable);
        return bookRepository.findAll(pageable);
    }

    private void verifySortProperties(Pageable pageable) {
        pageable.getSort().forEach(order -> BookSortField.validateSortField(order.getProperty()));
    }

    @Transactional(readOnly = true)
    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> CustomException.BOOK_NOT_FOUND);
    }

    @CacheEvict(value = "books", allEntries = true)
    @Transactional
    public void updateBook(Long bookId, BookUpdateCommand command) {
        bookRepository.findById(bookId)
            .ifPresentOrElse(
                book -> book.update(command.title(), command.author(), command.publisher(), command.isbn(), command.description(), command.publishedAt()),
                () -> {
                    throw CustomException.BOOK_NOT_FOUND;
                }
            );
    }

    @CacheEvict(value = "books", allEntries = true)
    @Transactional
    public void deleteBook(Long bookId) {
        bookRepository.findById(bookId)
            .ifPresentOrElse(
                bookRepository::delete,
                () -> {
                    throw CustomException.BOOK_NOT_FOUND;
                }
            );
    }

    @Cacheable(value = "books", key = "#keyword")
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }
}
