package org.querypie.bookmanagement.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.common.support.error.CustomException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {
    private static final String STRICT_EMAIL_REGEX = "^(?=.{1,64}@.{1,255}$)(?=.{6,255}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Column(name = "email")
    private String value;

    private Email(String value) {
        validate(value);
        this.value = normalize(value);
    }

    public static Email of(String email) {
        return new Email(email);
    }

    private void validate(String value) {
        if (value == null || !value.matches(STRICT_EMAIL_REGEX)) {
            throw CustomException.INVALID_EMAIL_FORMAT;
        }
    }

    private String normalize(String email) {
        String[] parts = email.split("@");
        return parts[0] + "@" + parts[1].toLowerCase(); // 도메인 부분을 소문자로 변환
    }
}
