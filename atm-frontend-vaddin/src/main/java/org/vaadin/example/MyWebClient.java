package org.vaadin.example;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MyWebClient {

    private final WebClient webClient;

    public String objectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert to JSON
            String json = objectMapper.writeValueAsString(object);
            System.out.println(json);
            return json;
        } catch (Exception e) {
            return "Invalid Object";
        }

    }
    
    public String register(RegisterRequest registerRequest) {

        return webClient
        .post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(registerRequest)
        .retrieve()
        .onStatus(
                HttpStatusCode::is4xxClientError, // Handling 4xx client errors
                clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(responseBody -> {
                        return Mono.error(new RuntimeException(responseBody));
                    })
            )
        .onStatus(
            HttpStatusCode::is5xxServerError, // Handling 5xx server errors
            clientResponse -> clientResponse.bodyToMono(String.class)
                .flatMap(responseBody -> {
                    return Mono.error(new RuntimeException(responseBody)); // Propagate the error
                })
        )
        .bodyToMono(String.class)
        .block();
    }


}
