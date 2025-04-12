package com.practice.microservices.controller;

import com.practice.microservices.dto.UserDto;
import com.practice.microservices.model.Post;
import com.practice.microservices.repository.PostRepository;
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
    private final PostRepository postRepository;

    private final RestTemplate restTemplate;


    public PostController(@Value("${users.server.url}") String url,
                          RestTemplateBuilder builder,
                          PostRepository postRepository) {
        restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();

        this.postRepository = postRepository;
    }

    @PostMapping
    public Post create(@RequestBody Post post, @RequestHeader("X-Authorized-User") int userId) {
        UserDto user = restTemplate.getForObject("/users/{id}", UserDto.class, Map.of("id", userId));
        System.out.println(user);

        post.setCreatedAt(LocalDateTime.now());
        post.setAuthorId(userId);

        postRepository.save(post);

        return post;
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable int postId) {
        return postRepository.findById(postId)
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
