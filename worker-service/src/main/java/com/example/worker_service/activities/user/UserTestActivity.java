package com.example.worker_service.activities.user;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface UserTestActivity {

  String testUserService();
}
