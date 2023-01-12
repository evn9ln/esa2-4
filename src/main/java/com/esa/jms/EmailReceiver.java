package com.esa.jms;

import com.esa.domain.EmailNotification;
import com.esa.domain.Logging;
import com.esa.repo.EmailNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailReceiver {

    @Autowired
    private EmailNotificationRepository emailNotificationRepository;

    @JmsListener(destination = "event", containerFactory = "myFactory")
    public void receive(Logging event) {
        String message = String.format(event.getEventType() + " action has performed on object of type " + event.getEntity());

        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setAddress("admin");
        emailNotification.setContent(message);

        emailNotificationRepository.save(emailNotification);

        System.out.println("JMS_MESSAGE_SENDER: Received new JMS message - " + message);
    }
}
