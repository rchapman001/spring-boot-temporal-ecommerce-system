package com.example.order_service.service;

import com.example.order_service.entity.Order;
import com.example.order_service.exception.ResourceNotFoundException;
import com.example.order_service.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired private OrderRepository orderRepository;

  public String testEndpoint() {
    return "testEndpoint OrderService";
  }

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  public Order getOrderById(String id) {
    return orderRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found"));
  }

  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }

  public Order updateStatus(String id, String status) {
    Order order = getOrderById(id);
    order.setStatus(status);
    return orderRepository.save(order);
  }

  public void deleteOrder(String id) {
    if (!orderRepository.existsById(id)) {
      throw new ResourceNotFoundException("Order " + id + " not found");
    }
    orderRepository.deleteById(id);
  }
}
