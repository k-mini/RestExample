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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestConfig {


    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        RestTemplate restTemplate = restTemplateBuilder
//                .setReadTimeout(Duration.ofMillis(3000)) // 읽기 시간
                .setConnectTimeout(Duration.ofMillis(3000)) // 연결 시간
                .requestFactory(() -> {
                    // 3.0 이상 requestFactory 사용할거면 위에 setReadTimeOut 주석처리
                    // setReadTimeOut 해당 속성은 삭제됨
                    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
                    factory.setConnectionRequestTimeout(5000);
                    factory.setConnectTimeout(3000);

                    factory.setHttpClient(createHttpClient());

                    BufferingClientHttpRequestFactory buffedFactory
                            = new BufferingClientHttpRequestFactory(factory);
                    return factory;
                })
//                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .build();

        restTemplate.getInterceptors().add(new RestTemplateLoggingRequestInterceptor());
        return restTemplate;
    }

    private HttpClient createHttpClient() {
        return org.apache.hc.client5.http.impl.classic
                .HttpClientBuilder.create()
                .setConnectionManager(createHttpClientConnectionManager())
                .build();
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
