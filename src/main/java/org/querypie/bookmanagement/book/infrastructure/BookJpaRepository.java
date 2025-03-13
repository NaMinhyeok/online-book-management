package org.querypie.bookmanagement.book.infrastructure;

import org.querypie.bookmanagement.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Long>, CustomBookRepository {
}
