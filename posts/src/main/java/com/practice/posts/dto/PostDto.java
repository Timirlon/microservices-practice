package com.practice.posts.dto;

import com.practice.posts.model.Post;
import lombok.Builder;

@Builder
public record PostDto(Integer id, String name, UserDto userDto) {
    public static PostDto of(Post post, UserDto userDto) {
        return builder()
                .id(post.getId())
                .name(post.getDescription())
                .userDto(userDto)
                .build();
    }
}
