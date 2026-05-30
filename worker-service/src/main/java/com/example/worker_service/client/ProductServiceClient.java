package com.example.worker_service.client;

import com.example.worker_service.dto.external.request.ProductExtRequest;
import com.example.worker_service.dto.external.response.ProductExtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductServiceClient {

  @Autowired private WebClient productServiceWebClient;

  public String testEndpoint() {
    return productServiceWebClient
        .get()
        .uri("/products/test")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public ProductExtResponse createProduct(ProductExtRequest productCreateRequest) {
    return productServiceWebClient
        .post()
        .uri("/products")
        .bodyValue(productCreateRequest)
        .retrieve()
        .bodyToMono(ProductExtResponse.class)
        .block();
  }

  public void deleteProduct(String productId) {
    productServiceWebClient
        .delete()
        .uri("/products/{productId}", productId)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }
}
