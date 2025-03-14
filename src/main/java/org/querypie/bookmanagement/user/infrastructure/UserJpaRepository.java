package org.querypie.bookmanagement.user.infrastructure;

import org.querypie.bookmanagement.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
