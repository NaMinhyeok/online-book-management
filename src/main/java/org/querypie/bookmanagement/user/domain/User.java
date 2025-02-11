package org.querypie.bookmanagement.user.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.querypie.bookmanagement.common.domain.BaseEntity;

@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    private String name;
    @Embedded
    private Email email;

    @Builder
    private User(String name, String email) {
        this.name = name;
        this.email = Email.of(email);
    }

    public static User create(String name, String email) {
        return User.builder()
            .name(name)
            .email(email)
            .build();
    }

    public String getEmail() {
        return email.getValue();
    }

}
