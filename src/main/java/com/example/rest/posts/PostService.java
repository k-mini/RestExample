package com.example.rest.posts;

import com.example.rest.posts.dto.PostCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Service
public class PostService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String,Object> insertPost(PostCreateDto dto) throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .path("posts")
                .build().toUri();

        RequestEntity<String> request = RequestEntity
                .post(uri)
                .contentType(APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(dto));

        ResponseEntity<Map<String, Object>> result =
                restTemplate.exchange(request, new ParameterizedTypeReference<Map<String, Object>>() {});

        return result.getBody();
    }
}
