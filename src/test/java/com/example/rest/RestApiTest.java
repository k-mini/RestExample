package com.example.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//@SpringBootTest
class RestApiTest {

	private final RestTemplate restTemplate = new RestTemplate();

	@Test
	public void restApi() {

		URI uri = UriComponentsBuilder
//				.newInstance()
//				.scheme("https")
//				.host("jsonplaceholder.typicode.com")
//				.port(443)
				.fromUriString("https://jsonplaceholder.typicode.com")
				.pathSegment("users", "1")
				.build().toUri();

		RequestEntity<Void> requestEntity = RequestEntity.get(uri)
				.build();
		ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
//		ResponseEntity<Map> response = restTemplate.exchange(requestEntity, Map.class);
		System.out.println("================== Headers ================");
		response.getHeaders()
				.entrySet()
				.stream().forEach(entry -> {
					System.out.println(String.format("%s : %s ", entry.getKey(), entry.getValue()));
				});
		System.out.println("==================================");
		System.out.println("response.getBody() = " + response.getBody());
	}

}


