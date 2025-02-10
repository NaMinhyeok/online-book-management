package org.querypie.bookmanagement.book.service;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.domain.BookCreateCommand;
import org.querypie.bookmanagement.book.repository.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public void registerBook(BookCreateCommand command) {
        bookRepository.save(Book.create(command));
    }
}
