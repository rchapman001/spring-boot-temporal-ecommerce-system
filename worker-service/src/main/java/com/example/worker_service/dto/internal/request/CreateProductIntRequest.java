package com.example.worker_service.dto.internal.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductIntRequest {

  private String name;
  private String description;
  private double price;
  private Integer quantityAvailable;
  private Integer quantityReserved;
}
