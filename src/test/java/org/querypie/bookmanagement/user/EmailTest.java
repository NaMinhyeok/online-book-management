package org.querypie.bookmanagement.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.user.domain.Email;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class EmailTest {

    @DisplayName("유효하지 않은 이메일을 검증한다")
    @ParameterizedTest(name = "이메일: {0}")
    @CsvSource(value = {
        "1234",
        "1234@naver",
        "1234@naver.",
        "나민혁@naver.com",
        "''",
        " test@example.com ",
        "test @example.com",
        "test@ example.com",
        "email@123.123.123.123",
        "test@example.com ",
        "NULL",
        "    test@example.com",
        "test@naver.com   "
    }, ignoreLeadingAndTrailingWhitespace = false)
    void createEmailValue_ShouldThrowException_WhenInvalidEmail(String email) {
        thenThrownBy(() -> Email.of(email))
            .isEqualTo(CustomException.INVALID_EMAIL_FORMAT);
    }

    @DisplayName("이메일은 NULL 일 수 없다")
    @Test
    void createEmailValueIsNotNULL() {
        //given
        //when
        thenThrownBy(() -> Email.of(null))
            .isEqualTo(CustomException.INVALID_EMAIL_FORMAT);
        //then
    }

    @DisplayName("유효한 이메일을 검증한다")
    @ParameterizedTest(name = "이메일: {0}")
    @CsvSource({
        "test@example.com",
        "user.name@domain.co.kr",
        "valid_email123@sub.domain.com",
        "firstname.lastname@company.com",
        "a@b.co",
        "longemailaddress@averylongdomainname.com",
        "user+name@domain.com",
        "user-name@domain.io",
        "user.name@domain.travel",
        "TEST@EXAMPLE.COM",
        "User.Name@DOMAIN.com",
        "UpperCase@Domain.Co",
        "verylongemailaddressnameexceeding64characters@domain.com",
        "user@domain.toolongtldname"
    })
    void createEmailValue_ShouldCreateEmail_WhenValidEmail(String email) {
        then(Email.of(email)).isNotNull();
    }

    @DisplayName("이메일의 `@` 뒷부분은 소문자로 변환한다")
    @Test
    void normalizeEmailAfterPart() {
        //given
        //when
        Email email = Email.of("hello@NAVER.COM");
        //then
        then(email.getValue()).isEqualTo("hello@naver.com");
    }

}