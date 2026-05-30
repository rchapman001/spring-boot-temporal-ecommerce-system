package com.example.worker_service.activities.user;

import com.example.worker_service.dto.external.response.UserExtResponse;
import io.temporal.activity.ActivityInterface;
import java.util.Optional;

@ActivityInterface
public interface GetUserActivity {
  Optional<UserExtResponse> getUserByEmail(String email);
}
