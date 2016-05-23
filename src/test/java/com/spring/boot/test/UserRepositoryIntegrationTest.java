package com.spring.boot.test;

import com.spring.boot.test.user.User;
import com.spring.boot.test.user.UserId;
import com.spring.boot.test.user.UserRepository;
import com.spring.boot.test.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository repository;

    @Test(expected = DataIntegrityViolationException.class)
    @Commit
    public void test() {
        repository.save(new User(new UserId(UUID.randomUUID()), "wim", "123456", UserRole.ADMIN));
        repository.save(new User(new UserId(UUID.randomUUID()), "wim", "123456", UserRole.ADMIN));
    }
}
