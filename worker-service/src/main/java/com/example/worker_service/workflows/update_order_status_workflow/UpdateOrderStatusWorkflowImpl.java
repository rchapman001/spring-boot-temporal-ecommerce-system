package com.example.worker_service.workflows.update_order_status_workflow;

import com.example.worker_service.activities.inventory.GetInventoryActivity;
import com.example.worker_service.activities.inventory.UpdateInventoryActivity;
import com.example.worker_service.activities.order.UpdateOrderStatusActivity;
import com.example.worker_service.dto.external.request.InventoryExtRequest;
import com.example.worker_service.dto.external.response.InventoryExtResponse;
import com.example.worker_service.dto.external.response.OrderExtResponse;
import com.example.worker_service.dto.internal.request.UpdateOrderStatusIntRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = "WORKER_TASK_QUEUE")
public class UpdateOrderStatusWorkflowImpl implements UpdateOrderStatusWorkflow {

  private final UpdateOrderStatusActivity updateOrderStatusActivity =
      Workflow.newActivityStub(
          UpdateOrderStatusActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final GetInventoryActivity getInventoryActivity =
      Workflow.newActivityStub(
          GetInventoryActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final UpdateInventoryActivity updateInventoryActivity =
      Workflow.newActivityStub(
          UpdateInventoryActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  @Override
  public String updateOrderStatus(UpdateOrderStatusIntRequest request) {

    // 1. Update Order Status
    OrderExtResponse updatedOrder =
        updateOrderStatusActivity.updateOrderStatus(request.getOrderId(), request.getStatus());

    // 3. Get Existing Inventory
    InventoryExtResponse existingInventory =
        getInventoryActivity.getInventoryByProductId(updatedOrder.getProductId());

    // 2. Handle COMPLETED Orders
    if ("COMPLETED".equalsIgnoreCase(updatedOrder.getStatus())) {

      InventoryExtRequest inventoryRequest = new InventoryExtRequest();

      inventoryRequest.setProductId(updatedOrder.getProductId());

      inventoryRequest.setQuantityAvailable(
          existingInventory.getQuantityAvailable() - updatedOrder.getQuantity());

      inventoryRequest.setQuantityReserved(
          existingInventory.getQuantityReserved() - updatedOrder.getQuantity());

      updateInventoryActivity.updateInventory(inventoryRequest);
    }

    // 5. Handle CANCELLED Orders
    if ("CANCELLED".equalsIgnoreCase(updatedOrder.getStatus())) {

      InventoryExtRequest inventoryRequest = new InventoryExtRequest();

      inventoryRequest.setProductId(updatedOrder.getProductId());

      inventoryRequest.setQuantityAvailable(existingInventory.getQuantityAvailable());

      inventoryRequest.setQuantityReserved(
          existingInventory.getQuantityReserved() - updatedOrder.getQuantity());

      updateInventoryActivity.updateInventory(inventoryRequest);
    }

    // 6. Return Workflow Result
    return """
        Order Updated Successfully

        Order ID: %s
        Product ID: %s
        Quantity: %d
        Status: %s
        """
        .formatted(
            updatedOrder.getId(),
            updatedOrder.getProductId(),
            updatedOrder.getQuantity(),
            updatedOrder.getStatus());
  }
}
