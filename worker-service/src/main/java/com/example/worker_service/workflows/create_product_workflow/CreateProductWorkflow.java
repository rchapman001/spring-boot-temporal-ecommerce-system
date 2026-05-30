package com.example.worker_service.workflows.create_product_workflow;

import com.example.worker_service.dto.internal.request.CreateProductIntRequest;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CreateProductWorkflow {

  @WorkflowMethod
  String createProduct(CreateProductIntRequest createProductIntRequest);
}
