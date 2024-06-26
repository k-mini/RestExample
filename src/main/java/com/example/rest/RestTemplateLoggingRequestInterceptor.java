package com.example.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestTemplateLoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        URI uri = request.getURI();
        traceRequest(request, body);

        ClientHttpResponse response = execution.execute(request, body);

        traceResponse(response, uri);

        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        StringBuilder reqLog = new StringBuilder();
        StringBuilder headerLog = new StringBuilder();

        request.getHeaders()
                .entrySet()
                .stream()
                .forEach((entry) -> {
                headerLog.append(String.format("%s : %s ",entry.getKey(), entry.getValue())).append("\n");
                });

        reqLog.append("[REQUEST] ================").append("\n")
                .append("Uri : ").append(request.getURI()).append("\n")
                .append("Method : ").append(request.getMethod()).append("\n").append("\n")
                .append("Header").append("\n").append(headerLog.toString()).append("\n")
                .append("Body").append("\n")
                .append(new String(body, StandardCharsets.UTF_8)).append("\n");
        System.out.print(reqLog.toString());
    }

    private void traceResponse(ClientHttpResponse response, URI uri) throws IOException {
        StringBuilder resLog = new StringBuilder();
        StringBuilder headerLog = new StringBuilder();
        response.getHeaders()
                .entrySet()
                .stream()
                .forEach((entry) -> {
                    headerLog.append(String.format("%s : %s ",entry.getKey(), entry.getValue())).append("\n");
                });

        resLog.append("[RESPONSE] ===============").append("\n")
                .append("Uri : ").append(uri).append("\n")
                .append("Status : ").append(response.getStatusCode()).append("\n").append("\n")
                .append("Header").append("\n").append(headerLog.toString()).append("\n")
                .append("Body").append("\n")
                .append(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8)).append("\n");
        System.out.println(resLog.toString());
    }
}
