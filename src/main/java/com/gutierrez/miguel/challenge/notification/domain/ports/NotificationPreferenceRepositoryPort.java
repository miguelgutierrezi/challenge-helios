package com.gutierrez.miguel.challenge.notification.domain.ports;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;

import java.util.List;
import java.util.UUID;

public interface NotificationPreferenceRepositoryPort {
    void save(NotificationPreferences preference);
    List<NotificationPreferences> findByUserId(UUID userId);
    boolean isEnabled(UUID userId, NotificationCategory category);
}
