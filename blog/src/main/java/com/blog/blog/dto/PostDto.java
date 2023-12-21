package com.blog.blog.dto;

import java.time.LocalDateTime;

public record PostDto(int id, String body, LocalDateTime createdAt, LocalDateTime updatedAt, String userEmail) {

}
