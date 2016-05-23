package com.spring.boot.test.user;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(UserId id);

    Optional<User> findByUsername(String username);
}
