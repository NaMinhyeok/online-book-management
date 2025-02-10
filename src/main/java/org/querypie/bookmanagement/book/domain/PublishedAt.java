package org.querypie.bookmanagement.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.common.support.error.CustomException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Embeddable
public class PublishedAt {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Column(name = "published_at", nullable = false)
    private LocalDate value;

    public PublishedAt(String value) {
        this.value = parseDate(value);
    }

    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value, FORMATTER);
        } catch (DateTimeException e) {
            throw CustomException.DATE_PARSE_ERROR;
        }
    }

    String getFormattedValue() {
        return value.format(FORMATTER);
    }
}
