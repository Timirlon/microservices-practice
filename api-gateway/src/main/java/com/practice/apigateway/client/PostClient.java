package com.practice.apigateway.client;

import com.practice.posts.model.Post;
import com.practice.users.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class PostClient {
    private final RestTemplate restTemplate;

    public PostClient(@Value("http://localhost:8082") String url,
                      RestTemplateBuilder builder) {

        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> findById(int id) {
        return restTemplate.getForEntity("/posts/{id}",
                Object.class,
                Map.of("id", id));
    }

    public ResponseEntity<Object> create(Post post, int userId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Authorized-User", String.valueOf(userId));
        HttpEntity<Post> entity = new HttpEntity<>(post, httpHeaders);

        return restTemplate.postForEntity("/posts", entity, Object.class);
    }
}
