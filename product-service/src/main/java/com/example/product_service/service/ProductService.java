package com.example.product_service.service;

import com.example.product_service.entity.Product;
import com.example.product_service.exception.ResourceNotFoundException;
import com.example.product_service.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  public String testEndpoint() {
    return "testEndpoint ProductService";
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product getProductById(String id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));
  }

  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(String id, Product product) {
    Product existing =
        productRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

    existing.setName(product.getName());
    existing.setDescription(product.getDescription());
    existing.setPrice(product.getPrice());

    return productRepository.save(existing);
  }

  public void deleteProduct(String id) {
    if (!productRepository.existsById(id)) {
      throw new ResourceNotFoundException("Product " + id + " not found");
    }
    productRepository.deleteById(id);
  }
}
