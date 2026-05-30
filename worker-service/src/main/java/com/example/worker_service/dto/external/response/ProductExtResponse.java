package com.example.worker_service.dto.external.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductExtResponse {
  private String id;
  private String name;
  private String description;
  private Double price;
}
