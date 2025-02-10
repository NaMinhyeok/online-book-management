package org.querypie.bookmanagement.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.common.domain.BaseEntity;

import java.time.LocalDate;

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

    public static Book create(BookCreateCommand command) {
        return Book.builder()
            .title(command.title())
            .author(command.author())
            .publisher(command.publisher())
            .isbn(command.isbn())
            .description(command.description())
            .publishedAt(command.publishedAt())
            .build();
    }

    public LocalDate getPublishedAt() {
        return publishedAt.getValue();
    }
}
