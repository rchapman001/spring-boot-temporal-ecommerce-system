package com.example.worker_service.activities.product;

import com.example.worker_service.client.ProductServiceClient;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class DeleteProductActivityImpl implements DeleteProductActivity {

  @Autowired private ProductServiceClient productServiceClient;

  @Override
  public void deleteProduct(String productId) {

    productServiceClient.deleteProduct(productId);
  }
}
