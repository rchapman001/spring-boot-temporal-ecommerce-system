package com.example.worker_service.activities.order;

import com.example.worker_service.dto.external.response.OrderExtResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface UpdateOrderStatusActivity {
  OrderExtResponse updateOrderStatus(String orderId, String status);
}
