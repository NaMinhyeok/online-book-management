package org.querypie.bookmanagement.user.service;

import lombok.RequiredArgsConstructor;
import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.repository.UserRepository;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void register(UserRegisterCommand command) {
        userRepository.save(User.create(command.name(), command.email()));
    }
}
