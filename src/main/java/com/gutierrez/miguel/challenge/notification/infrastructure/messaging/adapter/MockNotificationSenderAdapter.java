package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationSenderPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("mock")
public class MockNotificationSenderAdapter implements NotificationSenderPort {

    @Override
    public void send(Notification notification) {
        log.info("[MOCK] Notification simulated: Type={}, Recipient={}, Content={}",
                notification.getType(), notification.getRecipient(), notification.getContent());
    }
}
