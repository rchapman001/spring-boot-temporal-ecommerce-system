package com.example.worker_service.controller;

import com.example.worker_service.service.WorkflowService;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {

  @Autowired private WorkflowService workflowService;

  @GetMapping("/test")
  public String testEndpoint() {
    return workflowService.testEndpoint();
  }

  @GetMapping
  public Set<String> getWorkflowDefinitions() {
    return workflowService.getWorkflowDefinitions();
  }

  @GetMapping("/status/{id}")
  public Map<String, Object> getWorkflowStatus(@PathVariable String id) {
    return workflowService.getWorkflowStatus(id);
  }

  @PostMapping("/start-async/{workflowName}")
  public Map<String, Object> startWorkflowAsync(
      @PathVariable String workflowName, @RequestBody(required = false) Object input) {
    return workflowService.startWorkflowAsync(workflowName, input);
  }

  @PostMapping("/start-sync/{workflowName}")
  public Map<String, Object> startWorkflowSync(
      @PathVariable String workflowName, @RequestBody(required = false) Object input) {

    return workflowService.startWorkflowSync(workflowName, input);
  }

  @DeleteMapping("/{id}")
  public Map<String, Object> cancelWorkflow(@PathVariable String id) {
    return workflowService.cancelWorkflow(id);
  }
}
