package org.querypie.bookmanagement.book.domain;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.querypie.bookmanagement.common.support.error.CustomException;

import java.time.LocalDate;

import static org.assertj.core.api.BDDAssertions.then;

class PublishedAtTest {

    @DisplayName("PublishedAt 생성 메서드는")
    @Nested
    class newPublishedAtTest {

        @DisplayName("올바른 형식의 날짜인 경우")
        @Nested
        class shouldBeValidDate {

            @DisplayName("출판 날짜를 생성한다")
            @Test
            void createPublishedAt() {
                //given
                //when
                PublishedAt publishedAt = new PublishedAt("2021-01-01");
                //then
                then(publishedAt.getValue()).isEqualTo(LocalDate.of(2021, 1, 1));
            }
        }

        @DisplayName("잘못된 형식의 날짜인 경우")
        @Nested
        class shouldBeInvalidDate {
            @DisplayName("예외를 발생시킨다")
            @ParameterizedTest(name = "입력값: {0}")
            @CsvSource({
                "2021/01/01",
                "01-01-2021",
                "2021-13-01",
                "2021-02-33",
                "abcd-ef-gh",
                "''"
            })
            void invalidDateShouldThrowException(String inputDate) {
                BDDAssertions.thenThrownBy(() -> new PublishedAt(inputDate))
                    .isEqualTo(CustomException.DATE_PARSE_ERROR);
            }
        }
    }

}