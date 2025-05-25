package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationPreferenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaNotificationPreferencesRepositoryAdapter implements NotificationPreferenceRepositoryPort {

    private final JpaNotificationPreferencesRepository repository;
    private final NotificationPreferenceMapper mapper;

    @Override
    public void save(NotificationPreferences preference) {
        repository.save(mapper.toEntity(preference));
    }

    @Override
    public List<NotificationPreferences> findByCardholderId(UUID cardholderId) {
        return repository.findByCardholderId(cardholderId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean isEnabled(UUID cardholderId, NotificationCategory category) {
        return repository.findByCardholderIdAndCategory(cardholderId, category)
                .map(NotificationPreferencesEntity::isEnabled)
                .orElse(true);
    }
}