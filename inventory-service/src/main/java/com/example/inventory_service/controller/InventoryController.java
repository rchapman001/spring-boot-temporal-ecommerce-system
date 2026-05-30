package com.example.inventory_service.controller;

import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.service.InventoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

  @Autowired private InventoryService inventoryService;

  @GetMapping("/test")
  public String testEndpoint() {
    return inventoryService.testEndpoint();
  }

  @GetMapping
  public ResponseEntity<List<Inventory>> getAllInventory() {
    return ResponseEntity.ok(inventoryService.getAllInventory());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable String id) {
    Inventory inventory = inventoryService.getInventoryByProductId(id);
    return ResponseEntity.ok(inventory);
  }

  @PostMapping
  public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
    return ResponseEntity.ok(inventoryService.createInventory(inventory));
  }

  @PostMapping("/reserve/{productId}/{quantity}")
  public ResponseEntity<Inventory> reserveInventory(
      @PathVariable String productId, @PathVariable int quantity) {

    return ResponseEntity.ok(inventoryService.reserveInventory(productId, quantity));
  }

  @PostMapping("/release/{productId}/{quantity}")
  public ResponseEntity<Inventory> releaseInventory(
      @PathVariable String productId, @PathVariable int quantity) {

    return ResponseEntity.ok(inventoryService.releaseInventory(productId, quantity));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Inventory> updateInventory(
      @PathVariable String id, @RequestBody Inventory inventory) {
    Inventory updatedInventory = inventoryService.updateInventory(id, inventory);
    return ResponseEntity.ok(updatedInventory);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteInventory(@PathVariable String id) {
    inventoryService.deleteInventory(id);
    return ResponseEntity.noContent().build();
  }
}
