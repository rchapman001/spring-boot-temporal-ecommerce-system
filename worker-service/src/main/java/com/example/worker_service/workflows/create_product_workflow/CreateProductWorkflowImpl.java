package com.example.worker_service.workflows.create_product_workflow;

import com.example.worker_service.activities.inventory.CreateInventoryActivity;
import com.example.worker_service.activities.product.CreateProductActivity;
import com.example.worker_service.dto.external.request.InventoryExtRequest;
import com.example.worker_service.dto.external.request.ProductExtRequest;
import com.example.worker_service.dto.external.response.InventoryExtResponse;
import com.example.worker_service.dto.external.response.ProductExtResponse;
import com.example.worker_service.dto.internal.request.CreateProductIntRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = "WORKER_TASK_QUEUE")
public class CreateProductWorkflowImpl implements CreateProductWorkflow {

  private final CreateProductActivity createProductActivity =
      Workflow.newActivityStub(
          CreateProductActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final CreateInventoryActivity createInventoryActivity =
      Workflow.newActivityStub(
          CreateInventoryActivity.class,
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
  public String createProduct(CreateProductIntRequest createProductIntRequest) {

    // 1. Create ProductCreateRequest using CreateProductRequest
    ProductExtRequest productExtRequest = new ProductExtRequest();
    productExtRequest.setName(createProductIntRequest.getName());
    productExtRequest.setDescription(createProductIntRequest.getDescription());
    productExtRequest.setPrice(createProductIntRequest.getPrice());

    // 2. Create Product
    ProductExtResponse productExtResponse = createProductActivity.createProduct(productExtRequest);

    // 3. Create InventoryRequest using ProductResponse and ProductSetupRequest
    InventoryExtRequest inventoryExtRequest = new InventoryExtRequest();
    inventoryExtRequest.setProductId(productExtResponse.getId());
    inventoryExtRequest.setQuantityAvailable(createProductIntRequest.getQuantityAvailable());
    inventoryExtRequest.setQuantityReserved(createProductIntRequest.getQuantityReserved());

    // 4. Create Inventory (depends on productId)
    InventoryExtResponse inventoryExtResponse =
        createInventoryActivity.createInventory(inventoryExtRequest);

    // 5. Return combined response
    return """
        Product Setup Complete:
        -----------------------
        Product:
          ID: %s
          Name: %s
          Description: %s
          Price: %s

        Inventory:
          Product ID: %s
          Quantity Available: %d
          Quantity Reserved: %d
        """
        .formatted(
            productExtResponse.getId(),
            productExtResponse.getName(),
            productExtResponse.getDescription(),
            productExtResponse.getPrice(),
            inventoryExtResponse.getProductId(),
            inventoryExtResponse.getQuantityAvailable(),
            inventoryExtResponse.getQuantityReserved());
  }
}
