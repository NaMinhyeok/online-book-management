package org.querypie.bookmanagement.user.presentation.port;

import org.querypie.bookmanagement.user.domain.User;
import org.querypie.bookmanagement.user.service.command.UserRegisterCommand;

import java.util.List;

public interface UserService {
    void register(UserRegisterCommand command);

    List<User> getUsers();

    User getUser(Long id);
}
