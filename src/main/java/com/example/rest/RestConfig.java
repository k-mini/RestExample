package com.example.rest;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestConfig {


    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofMillis(3000)) // 읽기 시간
                .setConnectTimeout(Duration.ofMillis(3000)) // 연결 시간
//                .requestFactory(() -> {
//
//                    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//                    factory.setConnectTimeout(5000);
//                    factory.setConnectTimeout(3000);
//                    HttpClient httpClient = HttpClientBuilder.create()
//                            .setConnectionManager(createHttpClientConnectionManager())
//                            .build();
//                    factory.setHttpClient(httpClient);
//                    BufferingClientHttpRequestFactory buffedFactory
//                            = new BufferingClientHttpRequestFactory(factory);
//                    return factory;
//                })
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .build();

        restTemplate.getInterceptors().add(new RestTemplateLoggingRequestInterceptor());

        return restTemplate;
    }

    private HttpClientConnectionManager createHttpClientConnectionManager() {
        return PoolingHttpClientConnectionManagerBuilder
                .create()
                .setDefaultConnectionConfig(ConnectionConfig
                        .custom()
                        .setSocketTimeout(5000, TimeUnit.MILLISECONDS)
                        .setConnectTimeout(5000, TimeUnit.MILLISECONDS)
                        .build()
                )
                .build();
    }
}
