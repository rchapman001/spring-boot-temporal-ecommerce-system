package com.example.worker_service.activities.inventory;

import com.example.worker_service.client.InventoryServiceClient;
import com.example.worker_service.dto.external.request.InventoryExtRequest;
import com.example.worker_service.dto.external.response.InventoryExtResponse;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class CreateInventoryActivityImpl implements CreateInventoryActivity {

  @Autowired private InventoryServiceClient inventoryServiceClient;

  @Override
  public InventoryExtResponse createInventory(InventoryExtRequest createInventoryExtRequest) {
    return inventoryServiceClient.createInventory(createInventoryExtRequest);
  }
}
