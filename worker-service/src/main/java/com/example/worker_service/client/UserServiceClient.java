package com.example.worker_service.client;

import com.example.worker_service.dto.external.request.UserExtRequest;
import com.example.worker_service.dto.external.response.UserExtResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class UserServiceClient {

  @Autowired private WebClient userServiceWebClient;

  public String testEndpoint() {
    return userServiceWebClient
        .get()
        .uri("/users/test")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public Optional<UserExtResponse> getUserByEmail(String email) {
    try {
      UserExtResponse response =
          userServiceWebClient
              .get()
              .uri("/users/email/{email}", email)
              .retrieve()
              .bodyToMono(UserExtResponse.class)
              .block();
      return Optional.ofNullable(response);
    } catch (WebClientResponseException.NotFound e) {
      return Optional.empty();
    }
  }

  public UserExtResponse createUser(UserExtRequest createUserExtRequest) {
    return userServiceWebClient
        .post()
        .uri("/users")
        .bodyValue(createUserExtRequest)
        .retrieve()
        .bodyToMono(UserExtResponse.class)
        .block();
  }
}
