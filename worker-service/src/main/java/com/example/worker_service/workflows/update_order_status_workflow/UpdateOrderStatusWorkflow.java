package com.example.worker_service.workflows.update_order_status_workflow;

import com.example.worker_service.dto.internal.request.UpdateOrderStatusIntRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface UpdateOrderStatusWorkflow {

  @WorkflowMethod
  String updateOrderStatus(UpdateOrderStatusIntRequest request);
}
