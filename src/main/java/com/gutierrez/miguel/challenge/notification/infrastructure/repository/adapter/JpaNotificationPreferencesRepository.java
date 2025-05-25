package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaNotificationPreferencesRepository extends JpaRepository<NotificationPreferencesEntity, UUID> {

    List<NotificationPreferencesEntity> findByUserId(UUID userId);

    Optional<NotificationPreferencesEntity> findByUserIdAndCategory(UUID userId, NotificationCategory category);
}