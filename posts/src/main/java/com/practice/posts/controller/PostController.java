package com.practice.posts.controller;

import com.practice.posts.client.UserClient;
import com.practice.posts.dto.PostDto;
import com.practice.posts.dto.UserDto;
import com.practice.posts.model.Post;
import com.practice.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;

    private final UserClient userClient;

    @PostMapping
    public PostDto create(@RequestBody Post post, @RequestHeader("X-Authorized-User") int userId) {
        UserDto user = userClient.findById(userId);
        System.out.println(user);

        post.setCreatedAt(LocalDateTime.now());
        post.setAuthorId(userId);

        postRepository.save(post);

        return PostDto.of(post, user);
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
