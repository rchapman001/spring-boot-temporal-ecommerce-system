package com.example.worker_service.workflows.create_order_workflow;

import com.example.worker_service.dto.internal.request.CreateOrderIntRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CreateOrderWorkflow {

  @WorkflowMethod
  String createOrder(CreateOrderIntRequest createOrderIntRequest);
}
