package com.spring.boot.test.infrastructure.security;

import com.spring.boot.test.user.User;
import com.spring.boot.test.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
// ------------------------------ FIELDS ------------------------------

    private UserService userService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Autowired
    public DatabaseUserDetailsService(UserService userService) {
        this.userService = userService;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface UserDetailsService ---------------------

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userService.findByUsername(username);

        if (byUsername.isPresent()) {
            return new CustomUserDetails(byUsername.get());
        } else {
            throw new UsernameNotFoundException(format("User %s does not exist!", username));
        }
    }
}
