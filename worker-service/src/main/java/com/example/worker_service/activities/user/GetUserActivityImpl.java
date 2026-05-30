package com.example.worker_service.activities.user;

import com.example.worker_service.client.UserServiceClient;
import com.example.worker_service.dto.external.response.UserExtResponse;
import io.temporal.spring.boot.ActivityImpl;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class GetUserActivityImpl implements GetUserActivity {

  @Autowired private UserServiceClient userServiceClient;

  @Override
  public Optional<UserExtResponse> getUserByEmail(String email) {
    return userServiceClient.getUserByEmail(email);
  }
}
