package com.example.worker_service.dto.external.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductExtRequest {
  private String name;
  private String description;
  private Double price;
}
