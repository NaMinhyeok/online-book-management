package org.querypie.bookmanagement.book.domain;

import lombok.Getter;
import org.querypie.bookmanagement.common.support.error.CustomException;

import java.util.Arrays;

@Getter
public enum BookSortField {
    PUBLISHED_AT("publishedAt"),
    TITLE("title"),
    ;

    private final String field;

    BookSortField(String field) {
        this.field = field;
    }

    public static void validateSortField(String field) {
        Arrays.stream(BookSortField.values())
            .filter(sortField -> sortField.getField().equals(field))
            .findFirst()
            .orElseThrow(() -> CustomException.INVALID_SORT_FIELD);
    }

}
