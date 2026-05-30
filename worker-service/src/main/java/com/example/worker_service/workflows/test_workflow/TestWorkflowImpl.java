package com.example.worker_service.workflows.test_workflow;

import com.example.worker_service.activities.inventory.InventoryTestActivity;
import com.example.worker_service.activities.order.OrderTestActivity;
import com.example.worker_service.activities.product.ProductTestActivity;
import com.example.worker_service.activities.user.UserTestActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = "WORKER_TASK_QUEUE")
public class TestWorkflowImpl implements TestWorkflow {

  private final InventoryTestActivity inventoryActivity =
      Workflow.newActivityStub(
          InventoryTestActivity.class,
          ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofMinutes(5)).build());

  private final OrderTestActivity orderActivity =
      Workflow.newActivityStub(
          OrderTestActivity.class,
          ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofMinutes(5)).build());

  private final ProductTestActivity productActivity =
      Workflow.newActivityStub(
          ProductTestActivity.class,
          ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofMinutes(5)).build());

  private final UserTestActivity userActivity =
      Workflow.newActivityStub(
          UserTestActivity.class,
          ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofMinutes(5)).build());

  @Override
  public String runTestWorkflow() {

    String userResult = userActivity.testUserService();
    String productResult = productActivity.testProductService();
    String inventoryResult = inventoryActivity.testInventoryService();
    String orderResult = orderActivity.testOrderService();

    return """
        Workflow Results:
        -----------------
        User: %s
        Product: %s
        Inventory: %s
        Order: %s
        """
        .formatted(userResult, productResult, inventoryResult, orderResult);
  }
}
