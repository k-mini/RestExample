package com.example.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//@SpringBootTest
class RestApiTests {

	private final RestTemplate restTemplate = new RestTemplate();

	@Test
	void pathTest() {
		String domainUrl = "https://www.naver.com";
		String uri = "/users/";
		String uri2 = "/name";
		String uri3 = "key";

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromHttpUrl(domainUrl)
				.path("/abc/")     // path 사이에 /가 없다면 경로가 붙는다.
				.path("/de")
//				.pathSegment(uri3)
//				.pathSegment(uri, uri2, uri3)  // path 조각에 /있으면 인코딩 처리 abc, de -> abc/de  abc, /de -> abc%2F/de
				.queryParam("a", 1);

		UriComponents uriComponents = uriComponentsBuilder
//				.encode()
				.build();
		System.out.println("uriComponents.toString = " + uriComponents.toString());
		System.out.println("uriComponents.toUriString = " + uriComponents.toUriString());
	}

	@Test
	public void pathTest2() {

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.newInstance()
				.scheme("http")
				.host("www.naver.com/smserver")
				.path("user")
				.path("//test")
				.pathSegment("{id}");

		// 변수는 인코딩 되지 않음  EncodingHint.NONE
		String uri = uriComponentsBuilder
				.build()
				.expand("123+5")
				.toUriString();

		// 변수 템플릿까지 인코딩   EncodingHint.ENCODE_TEMPLATE
		String uri2 = uriComponentsBuilder
				.build("123+5")
				.toString();

		// uri1과 동일            EncodingHint.NONE
		String uri3 = uriComponentsBuilder
				.buildAndExpand("123+5")
				.toUriString();


		System.out.println("uri = " + uri);
		System.out.println("uri2 = " + uri2);
		System.out.println("uri3 = " + uri3);
	}

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


