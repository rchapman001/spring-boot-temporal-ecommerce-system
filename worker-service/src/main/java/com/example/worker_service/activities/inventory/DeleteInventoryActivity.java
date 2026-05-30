package com.example.worker_service.activities.inventory;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface DeleteInventoryActivity {
  void deleteInventory(String productId);
}
