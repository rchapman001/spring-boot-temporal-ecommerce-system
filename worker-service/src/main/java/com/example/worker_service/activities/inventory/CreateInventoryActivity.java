package com.example.worker_service.activities.inventory;

import com.example.worker_service.dto.external.request.InventoryExtRequest;
import com.example.worker_service.dto.external.response.InventoryExtResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CreateInventoryActivity {
  InventoryExtResponse createInventory(InventoryExtRequest createInventoryExtRequest);
}
