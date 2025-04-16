package com.practice.apigateway.controller;

import com.practice.apigateway.client.PostClient;
import com.practice.posts.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostClient postClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable int id) {
        return postClient.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Post post,
                                         @RequestHeader("X-Authorized-User") int userId) {
        return postClient.create(post, userId);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHttpRequest(HttpClientErrorException ex) {
        return new ResponseEntity<>(
                ex.getResponseBodyAsString(),
                ex.getResponseHeaders(),
                ex.getStatusCode());
    }
}
