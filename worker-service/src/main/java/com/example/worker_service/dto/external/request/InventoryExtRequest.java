package com.example.worker_service.dto.external.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryExtRequest {
  private String productId;
  private Integer quantityAvailable;
  private Integer quantityReserved;
}
