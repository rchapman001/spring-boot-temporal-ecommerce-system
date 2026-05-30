package com.example.worker_service.workflows.delete_product_workflow;

import com.example.worker_service.dto.internal.request.DeleteProductIntRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface DeleteProductWorkflow {

  @WorkflowMethod
  String deleteProduct(DeleteProductIntRequest request);
}
