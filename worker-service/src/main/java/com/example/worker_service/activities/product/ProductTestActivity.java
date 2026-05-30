package com.example.worker_service.activities.product;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ProductTestActivity {

  String testProductService();
}
