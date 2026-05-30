package com.example.worker_service.dto.external.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryExtResponse {
  private String productId;
  private Integer quantityAvailable;
  private Integer quantityReserved;
}
