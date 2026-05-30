package com.example.worker_service.activities.user;

import com.example.worker_service.dto.external.request.UserExtRequest;
import com.example.worker_service.dto.external.response.UserExtResponse;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CreateUserActivity {
  UserExtResponse createUser(UserExtRequest createUserExtRequest);
}
