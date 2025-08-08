package com.prolinktic.sgdea.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
public class AppConfigRestTemplate {

    private static final Logger log = LoggerFactory.getLogger(AppConfigRestTemplate.class);

    @Bean(name = "appRestClient")
    public RestTemplate getRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);

        RestTemplate restClient = new RestTemplate(
                new BufferingClientHttpRequestFactory(requestFactory));

        restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {

            log.info("=== REQUEST ===");
            log.info("Method: {}", request.getMethod());
            log.info("URL: {}", request.getURI());

            String query = request.getURI().getQuery();
            if (query != null) {
                log.info("Query Parameters: {}", query);
            }

            if (body != null && body.length > 0) {
                log.info("Body: {}", new String(body, StandardCharsets.UTF_8));
            }

            ClientHttpResponse response = execution.execute(request, body);
            log.info("=== RESPONSE ===");
            log.info("Status Code: {}", response.getStatusCode());
            log.info("Status Text: {}", response.getStatusText());

            try {
                String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                log.info("Body: {}", responseBody);
            } catch (IOException e) {
                log.error("Error reading response body", e);
            }

            return response;
        }));

        return restClient;
    }
}