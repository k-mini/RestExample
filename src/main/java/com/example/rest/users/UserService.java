package com.example.rest.users;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class UserService {

    public Map<String,Object> getUser(Long id) {

        RestTemplate restTemplate = new RestTemplate();

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
}
