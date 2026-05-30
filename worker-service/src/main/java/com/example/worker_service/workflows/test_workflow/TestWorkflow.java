package com.example.worker_service.workflows.test_workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TestWorkflow {

  @WorkflowMethod
  String runTestWorkflow();
}
