package com.spring.boot.test.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, UserId> {
    Optional<User> findByUsername(String username);
}
