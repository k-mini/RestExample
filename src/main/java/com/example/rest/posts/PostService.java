package com.example.rest.posts;

import com.example.rest.posts.dto.PostCreateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@RequiredArgsConstructor
@Service
public class PostService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public Map<String, Object> savePost(PostCreateDto dto) throws JsonProcessingException {

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

        ResponseEntity<Map<String, Object>> response =
                restTemplate.exchange(request, new ParameterizedTypeReference<Map<String, Object>>() {
                });

        return response.getBody();
    }

    /**
     * 스프링부트 3.2부터 추가된 rest client
     */
    public Map<String, Object> savePostVer2(PostCreateDto dto) throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .path("posts")
                .build().toUri();

        RestClient restClient = RestClient.create();

        ResponseEntity<Map<String, Object>> response = restClient
                .post()
                .uri(uri)
                .contentType(APPLICATION_JSON)
                .body(objectMapper.writeValueAsString(dto))
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {
                });

        return response.getBody();
    }

    /**
     *  multipart/form-data 예제 1
     *  web-flux 의존성 필요
     */
    public Map<String, Object> savePostWithMultiPart(PostCreateDto dto) throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .path("posts")
                .build().toUri();

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        // value part
        builder.part("value", objectMapper.writeValueAsString(dto), APPLICATION_JSON);

        // image part
        Resource resource = new FileSystemResource("C:\\Users\\kmin\\Downloads\\kakao.png");
        builder.part("image", resource, IMAGE_PNG)
                .header("X-Size", "400");

        MultiValueMap<String, HttpEntity<?>> multiPartBody = builder.build();

        RequestEntity<?> request = RequestEntity
                .post(uri)
                .contentType(MULTIPART_FORM_DATA)
                .body(multiPartBody);

        ResponseEntity<Map<String, Object>> response =
                restTemplate.exchange(request, new ParameterizedTypeReference<Map<String, Object>>() {
                });

        return response.getBody();
    }

    /**
     *  multipart/form-data 예제 2
     */
    public Map<String, Object> savePostWithMultiPartVer2(PostCreateDto dto) throws IOException {

        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .path("posts")
                .build().toUri();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // file part
        FileSystemResource fileSystemResource = new FileSystemResource("C:\\Users\\kmin\\Downloads\\kakao.png");
        body.add("file", fileSystemResource);

        // json part
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(APPLICATION_JSON);
        body.add("dto", new HttpEntity<>(objectMapper.writeValueAsString(dto), jsonHeaders));

        RequestEntity<MultiValueMap<String, Object>> request = RequestEntity
                .post(uri)
                .contentType(MULTIPART_FORM_DATA)
                .body(body);

        // Send the request
        ResponseEntity<Map<String, Object>> response =
                restTemplate.exchange(request, new ParameterizedTypeReference<Map<String, Object>>() {
                });

        // Handle the response
        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("File uploaded successfully!");
        } else {
            System.out.println("Failed to upload file. Response: " + response.getBody());
        }

        return response.getBody();
    }
}
