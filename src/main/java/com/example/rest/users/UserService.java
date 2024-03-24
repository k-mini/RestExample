package com.example.rest.users;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    public Map<String,Object> getUser(Long id) {

        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .path("/users/{id}")
                .buildAndExpand(id).toUri();

//        RequestEntity<Void> entity =
//                RequestEntity.get("https://jsonplaceholder.typicode.com/users/{id}", id)
//                .build();

        RequestEntity<Void> entity = RequestEntity.get(uri).build();

        ResponseEntity<Map<String, Object>> result =
                restTemplate.exchange(entity, new ParameterizedTypeReference<Map<String, Object>>() {});
        return result.getBody();
    }

    /**
     * 스프링부트 3.2부터 추가된 rest client
     */
    public Map<String,Object> getUser2(Long id) {

        URI uri = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("jsonplaceholder.typicode.com")
                .path("/users/{id}")
                .buildAndExpand(id).toUri();

        RestClient restClient = RestClient.create();

        ResponseEntity<Map<String, Object>> result = restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {});

        return result.getBody();
    }


}
