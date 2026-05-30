package com.example.worker_service.client;

import com.example.worker_service.dto.external.request.InventoryExtRequest;
import com.example.worker_service.dto.external.response.InventoryExtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class InventoryServiceClient {

  @Autowired private WebClient inventoryServiceWebClient;

  public String testEndpoint() {
    return inventoryServiceWebClient
        .get()
        .uri("/inventory/test")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  public InventoryExtResponse createInventory(InventoryExtRequest inventoryCreateRequest) {
    return inventoryServiceWebClient
        .post()
        .uri("/inventory")
        .bodyValue(inventoryCreateRequest)
        .retrieve()
        .bodyToMono(InventoryExtResponse.class)
        .block();
  }

  public void deleteInventory(String productId) {
    inventoryServiceWebClient
        .delete()
        .uri("/inventory/{productId}", productId)
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  public InventoryExtResponse reserveInventory(String productId, int quantity) {
    return inventoryServiceWebClient
        .post()
        .uri("/inventory/reserve/{productId}/{quantity}", productId, quantity)
        .retrieve()
        .bodyToMono(InventoryExtResponse.class)
        .block();
  }

  public InventoryExtResponse updateInventory(InventoryExtRequest InventoryExtRequest) {
    return inventoryServiceWebClient
        .put()
        .uri("/inventory/{id}", InventoryExtRequest.getProductId())
        .bodyValue(InventoryExtRequest)
        .retrieve()
        .bodyToMono(InventoryExtResponse.class)
        .block();
  }

  public InventoryExtResponse getInventoryByProductId(String productId) {
    return inventoryServiceWebClient
        .get()
        .uri("/inventory/{productId}", productId)
        .retrieve()
        .bodyToMono(InventoryExtResponse.class)
        .block();
  }
}
