package org.querypie.bookmanagement.book.service.port;

import org.querypie.bookmanagement.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Page<Book> findAll(Pageable pageable);

    Optional<Book> findById(Long id);

    void delete(Book book);

    List<Book> searchBooks(String keyword);

    List<Book> findAllById(List<Long> ids);
}
