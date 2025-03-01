package org.querypie.bookmanagement.book.repository;

import org.querypie.bookmanagement.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, CustomBookRepository {
}
