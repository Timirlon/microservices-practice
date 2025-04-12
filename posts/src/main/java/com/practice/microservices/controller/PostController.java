package com.practice.microservices.controller;

import com.practice.microservices.dto.UserDto;
import com.practice.microservices.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final RestTemplate restTemplate;
    private final List<Post> posts;
    private int nextId = 1;

    public PostController(@Value("${users.server.url}") String url, RestTemplateBuilder builder) {
        restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();

        posts = new ArrayList<>();
    }

    @PostMapping
    public Post create(@RequestBody Post post, @RequestHeader("X-Authorized-User") int userId) {
        UserDto user = restTemplate.getForObject("/users/{id}", UserDto.class, Map.of("id", userId));
        System.out.println(user);

        post.setId(nextId++);
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(user);
        posts.add(post);

        return post;
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable int postId) {
        return posts.stream()
                .filter(post -> post.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHttpRequest(HttpClientErrorException ex) {
        return new ResponseEntity<>(
                ex.getResponseBodyAsString(),
                ex.getResponseHeaders(),
                ex.getStatusCode());
    }
}
