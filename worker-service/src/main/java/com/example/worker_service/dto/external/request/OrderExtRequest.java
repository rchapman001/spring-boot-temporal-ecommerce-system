package com.example.worker_service.dto.external.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderExtRequest {
  private String userId;
  private String productId;
  private Integer quantity;
  private Double price;
  private String status; // CREATED, COMPLETED, CANCELLED
}
