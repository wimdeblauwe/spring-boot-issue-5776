package com.spring.boot.test.user.web;


import com.spring.boot.test.user.User;
import com.spring.boot.test.user.UserRole;

import java.util.Optional;

public class UserDto {
// ------------------------------ FIELDS ------------------------------

    private String id;
    private String username;
    private UserRole role;

// -------------------------- STATIC METHODS --------------------------

    public static UserDto fromUser(Optional<User> optional) {
        if (optional.isPresent()) {
            User user = optional.get();
            return new UserDto(user.getId().toString(), user.getUsername(), user.getRole());
        }
        return null;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public UserDto(String id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

// -------------------------- OTHER METHODS --------------------------

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }
}
