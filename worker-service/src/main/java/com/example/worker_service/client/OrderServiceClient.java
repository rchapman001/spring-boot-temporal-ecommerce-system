package com.example.worker_service.client;

import com.example.worker_service.dto.external.request.OrderExtRequest;
import com.example.worker_service.dto.external.response.OrderExtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderServiceClient {

  @Autowired private WebClient orderServiceWebClient;

  public String testEndpoint() {
    return orderServiceWebClient
        .get()
        .uri("/orders/test")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public OrderExtResponse createOrder(OrderExtRequest createOrderExtRequest) {
    return orderServiceWebClient
        .post()
        .uri("/orders")
        .bodyValue(createOrderExtRequest)
        .retrieve()
        .bodyToMono(OrderExtResponse.class)
        .block();
  }

  public OrderExtResponse updateOrderStatus(String orderId, String status) {
    return orderServiceWebClient
        .put()
        .uri(
            uriBuilder ->
                uriBuilder.path("/orders/{id}/status").queryParam("status", status).build(orderId))
        .retrieve()
        .bodyToMono(OrderExtResponse.class)
        .block();
  }
}
