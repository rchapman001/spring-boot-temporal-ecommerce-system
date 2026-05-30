package com.example.worker_service.activities.order;

import com.example.worker_service.dto.external.request.OrderExtRequest;
import com.example.worker_service.dto.external.response.OrderExtResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CreateOrderActivity {
  OrderExtResponse createOrder(OrderExtRequest createOrderExtRequest);
}
