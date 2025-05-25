package com.gutierrez.miguel.challenge.notification.application.usecases;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateNotificationPreferenceService {

    private final NotificationPreferenceRepositoryPort repository;

    public void updatePreference(UUID cardholderId, NotificationCategory category, boolean enabled) {
        NotificationPreferences preference = new NotificationPreferences(UUID.randomUUID(), cardholderId, category, enabled);
        repository.save(preference);
    }
}