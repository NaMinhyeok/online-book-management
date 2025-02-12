package org.querypie.bookmanagement.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.support.IntegrationTestSupport;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.repository.UserRepository;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.tuple;

@Transactional
class UserServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("사용자를 등록한다")
    @Test
    void registerUser() {
        //given
        //when
        userService.register(new UserRegisterCommand("나민혁", "nmh9097@gmail.com"));
        //then
        List<User> users = userRepository.findAll();
        then(users).extracting("name", "email")
            .containsExactly(tuple("나민혁", "nmh9097@gmail.com"));
    }

    @DisplayName("사용자를 모두 조회한다")
    @Test
    void getUsers() {
        //given
        User user1 = User.builder()
            .name("나민혁")
            .email("nmh9097@gmail.com")
            .build();
        User user2 = User.builder()
            .name("홍길동")
            .email("gildong@naver.com")
            .build();

        userRepository.saveAll(List.of(user1, user2));
        //when
        List<User> users = userService.getUsers();
        //then
        then(users).hasSize(2)
            .extracting("name", "email")
            .containsExactly(tuple("나민혁", "nmh9097@gmail.com"), tuple("홍길동", "gildong@naver.com"));
    }
}