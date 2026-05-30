package com.example.worker_service.dto.internal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderIntRequest {
  private String email;
  private String productId;
  private int quantity;
  private double price;
}
