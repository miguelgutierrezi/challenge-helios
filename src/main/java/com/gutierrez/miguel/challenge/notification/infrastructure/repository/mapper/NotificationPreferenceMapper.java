package com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationPreferenceMapper {

    public NotificationPreferencesEntity toEntity(NotificationPreferences preference) {
        return NotificationPreferencesEntity.builder()
                .id(preference.getId())
                .cardholderId(preference.getCardholderId())
                .category(preference.getCategory())
                .enabled(preference.isEnabled())
                .build();
    }

    public NotificationPreferences toDomain(NotificationPreferencesEntity entity) {
        return NotificationPreferences.builder()
                .id(entity.getId())
                .cardholderId(entity.getCardholderId())
                .category(entity.getCategory())
                .enabled(entity.isEnabled())
                .build();
    }
}