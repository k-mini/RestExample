package com.example.rest;

import com.example.rest.dto.MemberDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class EncodeTest {

    @Test
    void encode() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        MemberDto drew = MemberDto.builder()
                .name("drew")
                .age(10)
                .address("seoul")
                .build();

        String result = objectMapper.writeValueAsString(drew);
        System.out.println("result = " + result);

        byte[] bytes = Base64.getEncoder().encode(result.getBytes(ISO_8859_1));
        String encodedBase64FromBytes = new String(bytes, ISO_8859_1);
        // 위의 두 줄과 같은 기능
        String base64String = Base64.getEncoder().encodeToString(result.getBytes());
        System.out.println("base64String = " + base64String);
        System.out.println("bytes = " + encodedBase64FromBytes);

//        String d = new String(bytes, UTF_8);
//        String d = base64String;
//        System.out.println("d = " + d);
//        System.out.println("base64String = " + base64String);
        boolean equals = encodedBase64FromBytes.equals(base64String);
        boolean equals2 =
                "eyJhZGRyZXNzIjoic2VvdWwiLCJoZWlnaHQiOjE3NiwibWFuIjp0cnVlLCJuYW1lX2EiOiJkcmV3IiwiYWdlX2IiOjEwfQ=="
                        .equals(base64String);
        System.out.println("equals = " + equals);
        System.out.println("equals2 = " + equals2);
    }
}
