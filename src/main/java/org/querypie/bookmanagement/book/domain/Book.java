package org.querypie.bookmanagement.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.book.service.command.BookUpdateCommand;
import org.querypie.bookmanagement.common.domain.BaseEntity;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String isbn;

    private String description;

    @Embedded
    private PublishedAt publishedAt;

    @Builder
    private Book(String title, String author, String publisher, String isbn, String description, String publishedAt) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.description = description;
        this.publishedAt = new PublishedAt(publishedAt);
    }

    public static Book create(String title, String author, String publisher, String isbn, String description, String publishedAt) {
        return Book.builder()
            .title(title)
            .author(author)
            .publisher(publisher)
            .isbn(isbn)
            .description(description)
            .publishedAt(publishedAt)
            .build();
    }

    public void update(String title, String author, String publisher, String isbn, String description, String publishedAt) {
        Optional.ofNullable(title).ifPresent(value -> this.title = value);
        Optional.ofNullable(author).ifPresent(value -> this.author = value);
        Optional.ofNullable(publisher).ifPresent(value -> this.publisher = value);
        Optional.ofNullable(isbn).ifPresent(value -> this.isbn = value);
        Optional.ofNullable(description).ifPresent(value -> this.description = value);
        Optional.ofNullable(publishedAt).ifPresent(value -> this.publishedAt = new PublishedAt(value));
    }

    public LocalDate getPublishedAt() {
        return publishedAt.getValue();
    }

    public String getFormattedPublishedAt() {
        return publishedAt.getFormattedValue();
    }
}
