package com.practice.apigateway.client;

import com.practice.users.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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

    public ResponseEntity<Object> findById(int id) {
        return restTemplate.getForEntity("/users/{id}",
                Object.class,
                Map.of("id", id));
    }

    public ResponseEntity<Object> create(User user) {
        return restTemplate.postForEntity("/users", user, Object.class);
    }
}
