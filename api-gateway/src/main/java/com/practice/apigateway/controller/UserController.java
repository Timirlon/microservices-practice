package com.practice.apigateway.controller;

import com.practice.apigateway.client.UserClient;
import com.practice.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        return userClient.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User user) {
        return userClient.create(user);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHttpRequest(HttpClientErrorException ex) {
        return new ResponseEntity<>(
                ex.getResponseBodyAsString(),
                ex.getResponseHeaders(),
                ex.getStatusCode());
    }
}
