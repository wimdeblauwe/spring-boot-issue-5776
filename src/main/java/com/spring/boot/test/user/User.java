package com.spring.boot.test.user;


import com.spring.boot.test.domain.util.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
public class User extends AbstractEntity<UserId> {

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "[a-zA-Z_\\-\\.0-9]+")
    @Size(min = 1, max = 30)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    protected User() {
    }

    public User(UserId id, String username, String password, UserRole role) {
        super(id);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
