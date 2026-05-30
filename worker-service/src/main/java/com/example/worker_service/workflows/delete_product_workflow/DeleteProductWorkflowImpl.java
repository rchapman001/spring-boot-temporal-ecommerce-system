package com.example.worker_service.workflows.delete_product_workflow;

import com.example.worker_service.activities.inventory.DeleteInventoryActivity;
import com.example.worker_service.activities.product.DeleteProductActivity;
import com.example.worker_service.dto.internal.request.DeleteProductIntRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = "WORKER_TASK_QUEUE")
public class DeleteProductWorkflowImpl implements DeleteProductWorkflow {

  private final DeleteInventoryActivity deleteInventoryActivity =
      Workflow.newActivityStub(
          DeleteInventoryActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final DeleteProductActivity deleteProductActivity =
      Workflow.newActivityStub(
          DeleteProductActivity.class,
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
  public String deleteProduct(DeleteProductIntRequest deleteProductIntRequest) {

    // 1. Delete Inventory
    deleteInventoryActivity.deleteInventory(deleteProductIntRequest.getProductId());

    // 2. Delete Product
    deleteProductActivity.deleteProduct(deleteProductIntRequest.getProductId());

    // 3. Return Success Message
    return """
        Product Deletion Complete:
        --------------------------
        Deleted Product ID: %s
        """
        .formatted(deleteProductIntRequest.getProductId());
  }
}
