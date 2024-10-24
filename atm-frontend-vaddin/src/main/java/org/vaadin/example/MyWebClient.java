package org.vaadin.example;

import java.util.logging.Logger;

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
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final WebClient webClient;

    public String objectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert to JSON
            String json = objectMapper.writeValueAsString(object);
            logger.info(json);
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

    public String authenticate(AuthenticationRequest authenticationRequest) {

        return webClient
        .post()
        .uri("/api/v1/auth/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(authenticationRequest)
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

    public String getUserByToken(String token) {
        return webClient
            .get()
            .uri("/api/v1/users/by-token?token=" + token)
            .header("Authorization", "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(responseBody -> Mono.error(new RuntimeException(responseBody)))
            )
            .onStatus(
                HttpStatusCode::is5xxServerError,
                clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(responseBody -> Mono.error(new RuntimeException(responseBody)))
            )
            .bodyToMono(String.class)
            .block();
    }

    public String getAllAccountsForUser(String id, String token) {
        return webClient
            .get()
            .uri("/api/v1/users/"+id+"/accounts") // Assuming this is the endpoint for fetching user accounts
            .header("Authorization", "Bearer " + token) // Sending token in header
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(responseBody -> Mono.error(new RuntimeException(responseBody)))
            )
            .onStatus(
                HttpStatusCode::is5xxServerError,
                clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(responseBody -> Mono.error(new RuntimeException(responseBody)))
            )
            .bodyToMono(String.class)
            .block();
    }

    public String createTransaction(TransactionRequest transactionRequest, String token) {
        return webClient
            .post()
            .uri("/api/v1/transaction")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(transactionRequest)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(responseBody -> Mono.error(new RuntimeException(responseBody)))
            )
            .onStatus(
                HttpStatusCode::is5xxServerError,
                clientResponse -> clientResponse.bodyToMono(String.class)
                    .flatMap(responseBody -> Mono.error(new RuntimeException(responseBody)))
            )
            .bodyToMono(String.class)
            .block();
    }


}
