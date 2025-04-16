package com.practice.posts.client;

import com.practice.posts.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class UserClient {
    private final RestTemplate restTemplate;

    public UserClient(@Value("${users.server.url}") String url,
                      RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public UserDto findById(int id) {
        return restTemplate.getForObject("/users/{id}", UserDto.class, Map.of("id", id));
    }

    @ExceptionHandler
    public ResponseEntity<?> handleHttpRequest(HttpClientErrorException ex) {
        return new ResponseEntity<>(
                ex.getResponseBodyAsString(),
                ex.getResponseHeaders(),
                ex.getStatusCode());
    }
}
