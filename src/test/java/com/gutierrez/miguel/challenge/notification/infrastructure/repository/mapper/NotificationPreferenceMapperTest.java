package com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationPreferenceMapperTest {

    private NotificationPreferenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NotificationPreferenceMapper();
    }

    @Test
    void shouldMapDomainToEntity() {
        NotificationPreferences preference = NotificationPreferences.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .category(NotificationCategory.SOCIAL_EVENT)
                .enabled(true)
                .build();

        NotificationPreferencesEntity entity = mapper.toEntity(preference);

        assertEquals(preference.getId(), entity.getId());
        assertEquals(preference.getUserId(), entity.getUserId());
        assertEquals(preference.getCategory(), entity.getCategory());
        assertEquals(preference.isEnabled(), entity.isEnabled());
    }

    @Test
    void shouldMapEntityToDomain() {
        NotificationPreferencesEntity entity = NotificationPreferencesEntity.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .category(NotificationCategory.GAME_EVENT)
                .enabled(false)
                .build();

        NotificationPreferences preference = mapper.toDomain(entity);

        assertEquals(entity.getId(), preference.getId());
        assertEquals(entity.getUserId(), preference.getUserId());
        assertEquals(entity.getCategory(), preference.getCategory());
        assertEquals(entity.isEnabled(), preference.isEnabled());
    }
}