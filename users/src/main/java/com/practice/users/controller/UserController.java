package com.practice.users.controller;

import com.practice.users.model.User;
import com.practice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @PostMapping
    public User create(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        return user;
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
