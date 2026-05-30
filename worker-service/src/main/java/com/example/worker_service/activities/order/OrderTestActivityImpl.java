package com.example.worker_service.activities.order;

import com.example.worker_service.client.OrderServiceClient;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class OrderTestActivityImpl implements OrderTestActivity {

  @Autowired private OrderServiceClient orderServiceClient;

  @Override
  public String testOrderService() {
    return orderServiceClient.testEndpoint();
  }
}
