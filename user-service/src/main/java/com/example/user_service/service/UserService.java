package com.example.user_service.service;

import com.example.user_service.entity.User;
import com.example.user_service.exception.ResourceNotFoundException;
import com.example.user_service.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public String testEndpoint() {
    return "testEndpoint UserService";
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(String id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));
  }

  public User getUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new ResourceNotFoundException("User with email " + email + " not found"));
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User updateUser(String id, User user) {
    User existingUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));

    existingUser.setEmail(user.getEmail());

    return userRepository.save(existingUser);
  }

  public void deleteUser(String id) {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User " + id + " not found");
    }
    userRepository.deleteById(id);
  }
}
