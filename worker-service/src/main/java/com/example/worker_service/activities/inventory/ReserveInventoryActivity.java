package com.example.worker_service.activities.inventory;

import com.example.worker_service.dto.external.response.InventoryExtResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ReserveInventoryActivity {
  InventoryExtResponse reserveInventory(String productId, int quantity);
}
