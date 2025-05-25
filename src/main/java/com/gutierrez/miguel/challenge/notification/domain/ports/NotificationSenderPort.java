package com.gutierrez.miguel.challenge.notification.domain.ports;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;

public interface NotificationSenderPort {
    void send(Notification notification);
}
