package org.querypie.bookmanagement.user.service.port;

import org.querypie.bookmanagement.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);
}
