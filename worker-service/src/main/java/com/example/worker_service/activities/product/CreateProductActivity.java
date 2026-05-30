package com.example.worker_service.activities.product;

import com.example.worker_service.dto.external.request.ProductExtRequest;
import com.example.worker_service.dto.external.response.ProductExtResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CreateProductActivity {
  ProductExtResponse createProduct(ProductExtRequest createProductExtRequest);
}
