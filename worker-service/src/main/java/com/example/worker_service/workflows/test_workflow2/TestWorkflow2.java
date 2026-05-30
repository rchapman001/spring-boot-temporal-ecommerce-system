package com.example.worker_service.workflows.test_workflow2;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TestWorkflow2 {

  @WorkflowMethod
  String runTestWorkflow2();
}
