package com.example.worker_service.workflows.test_workflow2;

import com.example.worker_service.activities.inventory.InventoryTestActivity;
import com.example.worker_service.activities.order.OrderTestActivity;
import com.example.worker_service.activities.product.ProductTestActivity;
import com.example.worker_service.activities.user.UserTestActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = "WORKER_TASK_QUEUE")
public class TestWorkflowImpl2 implements TestWorkflow2 {

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
  public String runTestWorkflow2() {

    String orderResult = orderActivity.testOrderService();
    String userResult = userActivity.testUserService();
    String productResult = productActivity.testProductService();
    String inventoryResult = inventoryActivity.testInventoryService();

    return """
        Workflow Results:
        -----------------
        Order: %s
        User: %s
        Product: %s
        Inventory: %s
        """
        .formatted(orderResult, userResult, productResult, inventoryResult);
  }
}
