package com.example.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class Inventory {

  @Id
  @Column(name = "product_id", nullable = false)
  private String productId;

  @Column(name = "quantity_available", nullable = false)
  private Integer quantityAvailable;

  @Column(name = "quantity_reserved", nullable = false)
  private Integer quantityReserved;
}
