package com.gutierrez.miguel.challenge.notification.application.usecases;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.domain.ports.NotificationPreferenceRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateNotificationPreferenceServiceTest {

    @Mock
    private NotificationPreferenceRepositoryPort repository;

    @InjectMocks
    private UpdateNotificationPreferenceService service;

    private UUID userId;
    private NotificationCategory category;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        category = NotificationCategory.GAME_EVENT;
    }

    @Test
    void shouldUpdateExistingPreference() {
        NotificationPreferences existing = new NotificationPreferences(UUID.randomUUID(), userId, category, false);
        when(repository.findByUserIdAndCategory(userId, category)).thenReturn(existing);

        service.updatePreference(userId, category, true);

        assertTrue(existing.isEnabled());
        verify(repository).save(existing);
    }

    @Test
    void shouldCreateNewPreferenceIfNotExists() {
        when(repository.findByUserIdAndCategory(userId, category)).thenReturn(null);

        service.updatePreference(userId, category, true);

        ArgumentCaptor<NotificationPreferences> captor = ArgumentCaptor.forClass(NotificationPreferences.class);
        verify(repository).save(captor.capture());

        NotificationPreferences saved = captor.getValue();
        assertEquals(userId, saved.getUserId());
        assertEquals(category, saved.getCategory());
        assertTrue(saved.isEnabled());
    }

    @Test
    void shouldThrowExceptionOnRepositoryError() {
        when(repository.findByUserIdAndCategory(userId, category)).thenThrow(new RuntimeException("DB failure"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.updatePreference(userId, category, true));

        assertEquals("DB failure", exception.getMessage());
    }
}