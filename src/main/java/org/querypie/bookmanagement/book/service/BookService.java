package org.querypie.bookmanagement.book.service;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.domain.BookCreateCommand;
import org.querypie.bookmanagement.book.domain.BookUpdateCommand;
import org.querypie.bookmanagement.book.repository.BookRepository;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public void registerBook(BookCreateCommand command) {
        bookRepository.save(Book.create(command));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> CustomException.BOOK_NOT_FOUND);
    }

    @Transactional
    public void updateBook(Long bookId, BookUpdateCommand command) {
        bookRepository.findById(bookId)
            .ifPresentOrElse(
                book -> book.update(command),
                () -> {
                    throw CustomException.BOOK_NOT_FOUND;
                }
            );
    }
}
