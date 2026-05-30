package com.example.inventory_service.service;

import com.example.inventory_service.entity.Inventory;
import com.example.inventory_service.exception.ResourceNotFoundException;
import com.example.inventory_service.repository.InventoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

  @Autowired private InventoryRepository inventoryRepository;

  public String testEndpoint() {
    return "testEndpoint InventoryService";
  }

  public List<Inventory> getAllInventory() {
    return inventoryRepository.findAll();
  }

  public Inventory getInventoryByProductId(String id) {
    return inventoryRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Inventory not found for Product Id " + id));
  }

  public Inventory createInventory(Inventory inventory) {
    return inventoryRepository.save(inventory);
  }

  public Inventory reserveInventory(String productId, int quantity) {
    Inventory inventory =
        inventoryRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Inventory not found for product " + productId));

    if (inventory.getQuantityAvailable() < quantity) {
      throw new RuntimeException("Not enough stock available");
    }

    inventory.setQuantityReserved(inventory.getQuantityReserved() + quantity);

    return inventoryRepository.save(inventory);
  }

  public Inventory releaseInventory(String productId, int quantity) {
    Inventory inventory =
        inventoryRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Inventory not found for product " + productId));
    inventory.setQuantityReserved(inventory.getQuantityReserved() - quantity);

    return inventoryRepository.save(inventory);
  }

  public Inventory updateInventory(String id, Inventory inventory) {
    Inventory existingInventory =
        inventoryRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Inventory for Product Id " + id + " not found"));

    existingInventory.setProductId(inventory.getProductId());
    existingInventory.setQuantityAvailable(inventory.getQuantityAvailable());
    existingInventory.setQuantityReserved(inventory.getQuantityReserved());

    return inventoryRepository.save(existingInventory);
  }

  public void deleteInventory(String id) {
    if (!inventoryRepository.existsById(id)) {
      throw new ResourceNotFoundException("Inventory for Product Id " + id + " not found");
    }
    inventoryRepository.deleteById(id);
  }
}
