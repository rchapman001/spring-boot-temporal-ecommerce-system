package com.example.worker_service.activities.user;

import com.example.worker_service.client.UserServiceClient;
import com.example.worker_service.dto.external.request.UserExtRequest;
import com.example.worker_service.dto.external.response.UserExtResponse;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class CreateUserActivityImpl implements CreateUserActivity {

  @Autowired private UserServiceClient userServiceClient;

  @Override
  public UserExtResponse createUser(UserExtRequest createUserExtRequest) {
    return userServiceClient.createUser(createUserExtRequest);
  }
}
