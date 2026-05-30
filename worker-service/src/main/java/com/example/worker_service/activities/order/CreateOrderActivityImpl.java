package com.example.worker_service.activities.order;

import com.example.worker_service.client.OrderServiceClient;
import com.example.worker_service.dto.external.request.OrderExtRequest;
import com.example.worker_service.dto.external.response.OrderExtResponse;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class CreateOrderActivityImpl implements CreateOrderActivity {

  @Autowired private OrderServiceClient orderServiceClient;

  @Override
  public OrderExtResponse createOrder(OrderExtRequest createOrderExtRequest) {
    return orderServiceClient.createOrder(createOrderExtRequest);
  }
}
