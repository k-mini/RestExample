package com.example.rest.posts;

import com.example.rest.posts.dto.PostCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<?> post(PostCreateDto postCreateDto) throws JsonProcessingException {
        Map<String, Object> result = postService.savePost(postCreateDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/post/multipart")
    public ResponseEntity<?> multiPart(PostCreateDto postCreateDto) throws IOException {
        Map<String, Object> result = postService.savePostWithMultiPartVer2(postCreateDto);
        return ResponseEntity.ok(result);
    }
}
