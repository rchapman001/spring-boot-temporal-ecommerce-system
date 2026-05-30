package com.example.worker_service.activities.inventory;

import com.example.worker_service.client.InventoryServiceClient;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class DeleteInventoryActivityImpl implements DeleteInventoryActivity {

  @Autowired private InventoryServiceClient inventoryServiceClient;

  @Override
  public void deleteInventory(String productId) {
    inventoryServiceClient.deleteInventory(productId);
  }
}
