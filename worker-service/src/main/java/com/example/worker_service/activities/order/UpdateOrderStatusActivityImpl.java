package com.example.worker_service.activities.order;

import com.example.worker_service.client.OrderServiceClient;
import com.example.worker_service.dto.external.response.OrderExtResponse;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class UpdateOrderStatusActivityImpl implements UpdateOrderStatusActivity {

  @Autowired private OrderServiceClient orderServiceClient;

  @Override
  public OrderExtResponse updateOrderStatus(String orderId, String status) {
    return orderServiceClient.updateOrderStatus(orderId, status);
  }
}
