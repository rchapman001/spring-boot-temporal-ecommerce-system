package com.example.worker_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.temporal.client.*;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowService {

  private static final String WORKFLOW_PACKAGE = "com.example.worker_service.workflows";

  @Autowired private WorkflowClient workflowClient;

  @Autowired private ObjectMapper objectMapper;

  public String testEndpoint() {
    return "testEndpoint WorkflowService";
  }

  public Set<String> getWorkflowDefinitions() {
    return getWorkflowDefinitionsInternal().keySet();
  }

  public Map<String, Object> getWorkflowStatus(String workflowId) {

    WorkflowStub stub = workflowClient.newUntypedWorkflowStub(workflowId);

    WorkflowExecutionDescription description = stub.describe();

    return Map.of("workflowId", workflowId, "status", description.getStatus().name());
  }

  public Map<String, Object> startWorkflowAsync(String workflowName, Object input) {

    Map<String, Class<?>> workflowDefinitions = getWorkflowDefinitionsInternal();

    Class<?> workflowInterface = workflowDefinitions.get(workflowName);

    if (workflowInterface == null) {
      throw new IllegalArgumentException(
          "Unknown workflow: " + workflowName + ". Available: " + workflowDefinitions.keySet());
    }

    String workflowId = workflowName + "-" + UUID.randomUUID();

    WorkflowOptions options =
        WorkflowOptions.newBuilder()
            .setTaskQueue("WORKER_TASK_QUEUE")
            .setWorkflowId(workflowId)
            .build();

    Object workflowStub = workflowClient.newWorkflowStub(workflowInterface, options);

    Method workflowMethod =
        Arrays.stream(workflowInterface.getMethods())
            .filter(m -> m.isAnnotationPresent(WorkflowMethod.class))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No @WorkflowMethod found"));

    WorkflowClient.start(
        () -> {
          try {
            if (workflowMethod.getParameterCount() == 0) {
              return workflowMethod.invoke(workflowStub);
            } else {
              Class<?> paramType = workflowMethod.getParameterTypes()[0];
              Object convertedInput = objectMapper.convertValue(input, paramType);

              return workflowMethod.invoke(workflowStub, convertedInput);
            }
          } catch (Exception e) {
            throw new RuntimeException("Async workflow invocation failed", e);
          }
        });

    return Map.of(
        "workflowId", workflowId,
        "workflowType", workflowName,
        "status", "STARTED");
  }

  public Map<String, Object> startWorkflowSync(String workflowName, Object input) {

    Map<String, Class<?>> workflowDefinitions = getWorkflowDefinitionsInternal();

    Class<?> workflowInterface = workflowDefinitions.get(workflowName);

    if (workflowInterface == null) {
      throw new IllegalArgumentException(
          "Unknown workflow: " + workflowName + ". Available: " + workflowDefinitions.keySet());
    }

    String workflowId = workflowName + "-" + UUID.randomUUID();

    WorkflowOptions options =
        WorkflowOptions.newBuilder()
            .setTaskQueue("WORKER_TASK_QUEUE")
            .setWorkflowId(workflowId)
            .build();

    Object workflowStub = workflowClient.newWorkflowStub(workflowInterface, options);

    Method workflowMethod =
        Arrays.stream(workflowInterface.getMethods())
            .filter(m -> m.isAnnotationPresent(WorkflowMethod.class))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No @WorkflowMethod found"));

    try {
      Object result;

      if (workflowMethod.getParameterCount() == 0) {
        result = workflowMethod.invoke(workflowStub);
      } else {
        Class<?> paramType = workflowMethod.getParameterTypes()[0];

        Object convertedInput = objectMapper.convertValue(input, paramType);

        result = workflowMethod.invoke(workflowStub, convertedInput);
      }

      return Map.of(
          "workflowId", workflowId,
          "workflowType", workflowName,
          "status", "COMPLETED",
          "result", result);

    } catch (Exception e) {
      e.printStackTrace(); // 🔥 shows real error in logs
      throw new RuntimeException("Workflow execution failed", e);
    }
  }

  public Map<String, Object> cancelWorkflow(String workflowId) {

    WorkflowStub stub = workflowClient.newUntypedWorkflowStub(workflowId);

    stub.cancel();

    return Map.of("workflowId", workflowId, "status", "CANCEL_REQUESTED");
  }

  private Map<String, Class<?>> getWorkflowDefinitionsInternal() {

    Reflections reflections = new Reflections(WORKFLOW_PACKAGE);

    return reflections.getTypesAnnotatedWith(WorkflowImpl.class).stream()
        .flatMap(impl -> Arrays.stream(impl.getInterfaces()))
        .filter(iface -> iface.isAnnotationPresent(WorkflowInterface.class))
        .collect(Collectors.toMap(Class::getSimpleName, iface -> iface));
  }
}
