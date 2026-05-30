package com.example.worker_service.activities.email;

import com.example.worker_service.dto.SendEmailRequest;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SendEmailActivity {

  void sendEmail(SendEmailRequest request);
}
