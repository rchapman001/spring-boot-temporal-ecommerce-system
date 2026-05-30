package com.example.worker_service.activities.product;

import com.example.worker_service.client.ProductServiceClient;
import com.example.worker_service.dto.external.request.ProductExtRequest;
import com.example.worker_service.dto.external.response.ProductExtResponse;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class CreateProductActivityImpl implements CreateProductActivity {

  @Autowired private ProductServiceClient productServiceClient;

  @Override
  public ProductExtResponse createProduct(ProductExtRequest createProductExtRequest) {

    return productServiceClient.createProduct(createProductExtRequest);
  }
}
