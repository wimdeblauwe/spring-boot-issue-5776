package com.spring.boot.test.user.web;

import com.spring.boot.test.infrastructure.security.CustomUserDetails;
import com.spring.boot.test.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDto getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return UserDto.fromUser(userService.findById(userDetails.getId()));
    }
}