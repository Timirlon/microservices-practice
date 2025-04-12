package com.practice.microservices.model;

import com.practice.microservices.dto.UserDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    Integer id;
    String description;
    UserDto author;
    LocalDateTime createdAt;
}
