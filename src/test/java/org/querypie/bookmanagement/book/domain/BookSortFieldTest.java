package org.querypie.bookmanagement.book.domain;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.common.support.error.CustomException;

class BookSortFieldTest {

    @DisplayName("입력받은 필드값이 유효하지 않으면 예외를 던진다.")
    @Test
    void validateSortField() {
        //when
        //then
        BDDAssertions.thenThrownBy(() -> BookSortField.validateSortField("author"))
            .isEqualTo(CustomException.INVALID_SORT_FIELD);
    }

}