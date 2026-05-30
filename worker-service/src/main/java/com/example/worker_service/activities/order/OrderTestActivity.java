package com.example.worker_service.activities.order;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface OrderTestActivity {

  String testOrderService();
}
