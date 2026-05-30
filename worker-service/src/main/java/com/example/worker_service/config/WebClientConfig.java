package com.example.worker_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value("${services.user-service.url}")
  private String userServiceUrl;

  @Value("${services.product-service.url}")
  private String productServiceUrl;

  @Value("${services.inventory-service.url}")
  private String inventoryServiceUrl;

  @Value("${services.order-service.url}")
  private String orderServiceUrl;

  @Bean
  public WebClient userServiceWebClient(WebClient.Builder builder) {
    return builder.baseUrl(userServiceUrl).build();
  }

  @Bean
  public WebClient productServiceWebClient(WebClient.Builder builder) {
    return builder.baseUrl(productServiceUrl).build();
  }

  @Bean
  public WebClient inventoryServiceWebClient(WebClient.Builder builder) {
    return builder.baseUrl(inventoryServiceUrl).build();
  }

  @Bean
  public WebClient orderServiceWebClient(WebClient.Builder builder) {
    return builder.baseUrl(orderServiceUrl).build();
  }
}
