package com.example.worker_service.activities.user;

import com.example.worker_service.client.UserServiceClient;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class UserTestActivityImpl implements UserTestActivity {

  @Autowired private UserServiceClient userServiceClient;

  @Override
  public String testUserService() {
    return userServiceClient.testEndpoint();
  }
}
