package org.querypie.bookmanagement.book.infrastructure;

import org.querypie.bookmanagement.book.domain.Book;

import java.util.List;

public interface CustomBookRepository {

    List<Book> searchBooks(String keyword);

}
