package org.querypie.bookmanagement.user.service;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.common.aop.Trace;
import org.querypie.bookmanagement.common.support.error.CustomException;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.presentation.port.UserService;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;
import org.querypie.bookmanagement.user.service.port.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void register(UserRegisterCommand command) {
        userRepository.save(User.create(command.name(), command.email()));
    }

    @Trace
    @Cacheable(value = "users")
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Trace
    @Cacheable(value = "users", key = "#id")
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> CustomException.USER_NOT_FOUND);
    }
}
