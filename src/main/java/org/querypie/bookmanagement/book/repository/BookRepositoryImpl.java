package org.querypie.bookmanagement.book.repository;

import org.querypie.bookmanagement.book.domain.Book;
import org.querypie.bookmanagement.book.service.port.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    public BookRepositoryImpl(BookJpaRepository bookJpaRepository) {
        this.bookJpaRepository = bookJpaRepository;
    }

    @Override
    public void save(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public void delete(Book book) {
        bookJpaRepository.delete(book);
    }

    @Override
    public List<Book> searchBooks(String keyword) {
        return bookJpaRepository.searchBooks(keyword);
    }

    @Override
    public List<Book> findAllById(List<Long> ids) {
        return bookJpaRepository.findAllById(ids);
    }
}
