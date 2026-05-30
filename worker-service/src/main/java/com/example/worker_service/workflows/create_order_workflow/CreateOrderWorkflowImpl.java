package com.example.worker_service.workflows.create_order_workflow;

import com.example.worker_service.activities.email.SendEmailActivity;
import com.example.worker_service.activities.inventory.ReserveInventoryActivity;
import com.example.worker_service.activities.order.CreateOrderActivity;
import com.example.worker_service.activities.user.CreateUserActivity;
import com.example.worker_service.activities.user.GetUserActivity;
import com.example.worker_service.dto.SendEmailRequest;
import com.example.worker_service.dto.external.request.OrderExtRequest;
import com.example.worker_service.dto.external.request.UserExtRequest;
import com.example.worker_service.dto.external.response.InventoryExtResponse;
import com.example.worker_service.dto.external.response.OrderExtResponse;
import com.example.worker_service.dto.external.response.UserExtResponse;
import com.example.worker_service.dto.internal.request.CreateOrderIntRequest;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import java.util.Optional;

@WorkflowImpl(taskQueues = "WORKER_TASK_QUEUE")
public class CreateOrderWorkflowImpl implements CreateOrderWorkflow {

  private final GetUserActivity getUserActivity =
      Workflow.newActivityStub(
          GetUserActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final CreateUserActivity createUserActivity =
      Workflow.newActivityStub(
          CreateUserActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final ReserveInventoryActivity reserveInventoryActivity =
      Workflow.newActivityStub(
          ReserveInventoryActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final CreateOrderActivity createOrderActivity =
      Workflow.newActivityStub(
          CreateOrderActivity.class,
          ActivityOptions.newBuilder()
              .setStartToCloseTimeout(Duration.ofMinutes(2))
              .setRetryOptions(
                  RetryOptions.newBuilder()
                      .setInitialInterval(Duration.ofSeconds(2))
                      .setBackoffCoefficient(2.0)
                      .setMaximumAttempts(5)
                      .build())
              .build());

  private final SendEmailActivity sendEmailActivity =
      Workflow.newActivityStub(
          SendEmailActivity.class,
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
  public String createOrder(CreateOrderIntRequest createOrderIntRequest) {

    // 1. Find Existing User
    Optional<UserExtResponse> existingUserExtResponse =
        getUserActivity.getUserByEmail(createOrderIntRequest.getEmail());

    UserExtResponse user;

    // 2. Create User If Not Found
    if (existingUserExtResponse.isPresent()) {

      user = existingUserExtResponse.get();

    } else {

      UserExtRequest userExtRequest = new UserExtRequest();

      userExtRequest.setEmail(createOrderIntRequest.getEmail());

      user = createUserActivity.createUser(userExtRequest);
    }

    // 3. Reserve Inventory
    InventoryExtResponse inventoryExtResponse =
        reserveInventoryActivity.reserveInventory(
            createOrderIntRequest.getProductId(), createOrderIntRequest.getQuantity());

    // 4. Create Order
    OrderExtRequest orderExtRequest = new OrderExtRequest();

    orderExtRequest.setUserId(user.getId());

    orderExtRequest.setProductId(createOrderIntRequest.getProductId());

    orderExtRequest.setQuantity(createOrderIntRequest.getQuantity());

    orderExtRequest.setPrice(createOrderIntRequest.getPrice());

    orderExtRequest.setStatus("CREATED");

    OrderExtResponse orderResponse = createOrderActivity.createOrder(orderExtRequest);

    // 5. Send Confirmation Email
    SendEmailRequest emailRequest = new SendEmailRequest();

    emailRequest.setTo(createOrderIntRequest.getEmail());

    emailRequest.setSubject("Order Confirmation");

    emailRequest.setBody(
        """
        Your order was placed successfully!

        Order ID: %s
        Status: %s
        """
            .formatted(orderResponse.getId(), orderResponse.getStatus()));

    sendEmailActivity.sendEmail(emailRequest);

    // 6. Return Response
    return """
        Order Created Successfully:
        ---------------------------

        User:
          ID: %s
          Email: %s

        Order:
          ID: %s
          Product ID: %s
          Status: %s

        Inventory:
          Product ID: %s
          Quantity Available: %d
          Quantity Reserved: %d
        """
        .formatted(
            user.getId(),
            user.getEmail(),
            orderResponse.getId(),
            orderResponse.getProductId(),
            orderResponse.getStatus(),
            inventoryExtResponse.getProductId(),
            inventoryExtResponse.getQuantityAvailable(),
            inventoryExtResponse.getQuantityReserved());
  }
}
