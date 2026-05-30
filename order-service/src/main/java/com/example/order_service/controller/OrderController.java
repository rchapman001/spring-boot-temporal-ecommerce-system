package com.example.order_service.controller;

import com.example.order_service.entity.Order;
import com.example.order_service.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired private OrderService orderService;

  @GetMapping("/test")
  public String testEndpoint() {
    return orderService.testEndpoint();
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    return ResponseEntity.ok(orderService.getAllOrders());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrder(@PathVariable String id) {
    return ResponseEntity.ok(orderService.getOrderById(id));
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    return ResponseEntity.ok(orderService.createOrder(order));
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<Order> updateStatus(@PathVariable String id, @RequestParam String status) {

    return ResponseEntity.ok(orderService.updateStatus(id, status));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
