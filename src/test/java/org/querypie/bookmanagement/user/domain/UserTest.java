package org.querypie.bookmanagement.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class UserTest {

    @DisplayName("사용자를 생성한다")
    @Test
    void createUser() {
        //given
        //when
        User user = User.create("나민혁", "nmh9097@gmail.com");
        //then
        then(user).extracting("name", "email")
            .containsExactly("나민혁", "nmh9097@gmail.com");
    }

}