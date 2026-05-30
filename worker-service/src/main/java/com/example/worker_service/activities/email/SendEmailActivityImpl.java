package com.example.worker_service.activities.email;

import com.example.worker_service.dto.SendEmailRequest;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "WORKER_TASK_QUEUE")
public class SendEmailActivityImpl implements SendEmailActivity {

  @Override
  public void sendEmail(SendEmailRequest request) {

    System.out.println(
        """
        ===================================
        ORDER CONFIRMATION EMAIL
        ===================================

        TO: %s

        SUBJECT: %s

        BODY:
        %s

        ===================================
        """
            .formatted(request.getTo(), request.getSubject(), request.getBody()));
  }
}
