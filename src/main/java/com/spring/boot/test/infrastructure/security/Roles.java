package com.spring.boot.test.infrastructure.security;

/**
 * This interface is a convenience for working with the Spring @Secured method
 * The names have to match with the {@link com.pegusapps.buildunits.user.UserRole} enum.
 */
public interface Roles {
    String ADMIN = "ROLE_ADMIN";
    String REPRESENTATIVE = "ROLE_REPRESENTATIVE";
}
