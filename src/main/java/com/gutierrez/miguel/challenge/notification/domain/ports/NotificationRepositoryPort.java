package com.gutierrez.miguel.challenge.notification.domain.ports;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;

public interface NotificationRepositoryPort {
    Notification save(Notification notification);

}
