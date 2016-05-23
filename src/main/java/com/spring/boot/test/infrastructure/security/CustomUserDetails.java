package com.spring.boot.test.infrastructure.security;

import com.google.common.collect.Sets;
import com.spring.boot.test.user.User;
import com.spring.boot.test.user.UserId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private UserId id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        authorities = Sets.newHashSet(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserId getId() {
        return id;
    }
}
