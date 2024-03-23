package com.example.rest.posts.dto;

import lombok.Data;

@Data
public class PostCreateDto {

    private String title;
    private String body;
    private Long userId;
}
