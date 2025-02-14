package org.querypie.bookmanagement.user.service;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.common.aop.Trace;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.repository.UserRepository;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void register(UserRegisterCommand command) {
        userRepository.save(User.create(command.name(), command.email()));
    }

    @Trace
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Trace
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> CustomException.USER_NOT_FOUND);
    }
}
