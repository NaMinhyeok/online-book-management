package org.querypie.bookmanagement.book.service;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.presentation.port.BookCommandService;
import org.querypie.bookmanagement.book.service.command.BookCreateCommand;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;
import org.querypie.bookmanagement.book.service.port.BookRepository;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    @CacheEvict(value = "books", allEntries = true)
    @Override
    public void registerBook(BookCreateCommand command) {
        bookRepository.save(Book.create(command.title(), command.author(), command.publisher(), command.isbn(), command.description(), command.publishedAt()));
    }

    @CacheEvict(value = "books", allEntries = true)
    @Transactional
    @Override
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
    @Override
    public void deleteBook(Long bookId) {
        bookRepository.findById(bookId)
            .ifPresentOrElse(
                bookRepository::delete,
                () -> {
                    throw CustomException.BOOK_NOT_FOUND;
                }
            );
    }
}
