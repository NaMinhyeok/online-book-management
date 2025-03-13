package org.querypie.bookmanagement.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.support.IntegrationTestSupport;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.infrastructure.UserJpaRepository;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.*;

@Transactional
class UserServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @DisplayName("사용자를 등록한다")
    @Test
    void registerUser() {
        //given
        //when
        userServiceImpl.register(new UserRegisterCommand("나민혁", "nmh9097@gmail.com"));
        //then
        List<User> users = userJpaRepository.findAll();
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

        userJpaRepository.saveAll(List.of(user1, user2));
        //when
        List<User> users = userServiceImpl.getUsers();
        //then
        then(users).hasSize(2)
            .extracting("name", "email")
            .containsExactly(tuple("나민혁", "nmh9097@gmail.com"), tuple("홍길동", "gildong@naver.com"));
    }

    @DisplayName("getUser 메서드는")
    @Nested
    class getUser {
        @DisplayName("존재하는 사용자를 조회하면")
        @Nested
        class userExists {
            @DisplayName("사용자를 반환한다")
            @Test
            void findUser() {
                //given
                User user = User.builder()
                    .name("나민혁")
                    .email("nmh9097@gmail.com")
                    .build();
                userJpaRepository.save(user);
                //when
                User foundUser = userServiceImpl.getUser(user.getId());
                //then
                then(foundUser).extracting("name", "email")
                    .containsExactly("나민혁", "nmh9097@gmail.com");
            }
        }

        @DisplayName("존재하지 않는 사용자를 조회하면")
        @Nested
        class userNotExists {
            @DisplayName("사용자를 찾을 수 없다는 예외를 던진다")
            @Test
            void throwUserNotFoundException() {
                //when
                //then
                thenThrownBy(() -> userServiceImpl.getUser(0L))
                    .isEqualTo(CustomException.USER_NOT_FOUND);
            }
        }
    }
}