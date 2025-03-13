package org.querypie.bookmanagement.book.presentation.port;

import org.querypie.bookmanagement.book.service.command.BookCreateCommand;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;

public interface BookCommandService {
    void registerBook(BookCreateCommand command);

    void updateBook(Long bookId, BookUpdateCommand command);

    void deleteBook(Long bookId);
}
