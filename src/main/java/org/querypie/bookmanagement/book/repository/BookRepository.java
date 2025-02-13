package org.querypie.bookmanagement.book.repository;

import org.querypie.bookmanagement.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByIsbnIn(List<String> isbns);

}
