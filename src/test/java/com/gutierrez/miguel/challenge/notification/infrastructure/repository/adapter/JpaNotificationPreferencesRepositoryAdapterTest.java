package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationPreferences;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationPreferencesEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationPreferenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaNotificationPreferencesRepositoryAdapterTest {

    @Mock
    private JpaNotificationPreferencesRepository repository;

    @Mock
    private NotificationPreferenceMapper mapper;

    @InjectMocks
    private JpaNotificationPreferencesRepositoryAdapter adapter;

    private UUID userId;
    private NotificationCategory category;
    private NotificationPreferences domainPref;
    private NotificationPreferencesEntity entityPref;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        category = NotificationCategory.GAME_EVENT;

        domainPref = new NotificationPreferences(UUID.randomUUID(), userId, category, true);
        entityPref = NotificationPreferencesEntity.builder()
                .id(domainPref.getId())
                .userId(userId)
                .category(category)
                .enabled(true)
                .build();
    }

    @Test
    void shouldSaveNotificationPreference() {
        when(mapper.toEntity(domainPref)).thenReturn(entityPref);

        adapter.save(domainPref);

        verify(repository).save(entityPref);
    }

    @Test
    void shouldFindPreferencesByUserId() {
        when(repository.findByUserId(userId)).thenReturn(List.of(entityPref));
        when(mapper.toDomain(entityPref)).thenReturn(domainPref);

        List<NotificationPreferences> result = adapter.findByUserId(userId);

        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
        verify(repository).findByUserId(userId);
        verify(mapper).toDomain(entityPref);
    }

    @Test
    void shouldReturnTrueWhenPreferenceNotFound() {
        when(repository.findByUserIdAndCategory(userId, category)).thenReturn(Optional.empty());

        boolean enabled = adapter.isEnabled(userId, category);

        assertTrue(enabled);
    }

    @Test
    void shouldReturnActualEnabledStatusWhenFound() {
        entityPref.setEnabled(false);
        when(repository.findByUserIdAndCategory(userId, category)).thenReturn(Optional.of(entityPref));

        boolean enabled = adapter.isEnabled(userId, category);

        assertFalse(enabled);
    }

    @Test
    void shouldFindByUserIdAndCategory() {
        when(repository.findByUserIdAndCategory(userId, category)).thenReturn(Optional.of(entityPref));
        when(mapper.toDomain(entityPref)).thenReturn(domainPref);

        NotificationPreferences result = adapter.findByUserIdAndCategory(userId, category);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
    }

    @Test
    void shouldReturnNullIfNoPreferenceFoundByUserIdAndCategory() {
        when(repository.findByUserIdAndCategory(userId, category)).thenReturn(Optional.empty());

        NotificationPreferences result = adapter.findByUserIdAndCategory(userId, category);

        assertNull(result);
    }

    @Test
    void shouldThrowExceptionWhenSaveFails() {
        when(mapper.toEntity(domainPref)).thenReturn(entityPref);
        doThrow(new RuntimeException("DB failure")).when(repository).save(entityPref);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adapter.save(domainPref));
        assertEquals("DB failure", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFindByUserIdFails() {
        when(repository.findByUserId(userId)).thenThrow(new RuntimeException("DB failure"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adapter.findByUserId(userId));
        assertEquals("DB failure", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIsEnabledFails() {
        when(repository.findByUserIdAndCategory(userId, category))
                .thenThrow(new RuntimeException("DB failure"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adapter.isEnabled(userId, category));
        assertEquals("DB failure", thrown.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFindByUserIdAndCategoryFails() {
        when(repository.findByUserIdAndCategory(userId, category))
                .thenThrow(new RuntimeException("DB failure"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> adapter.findByUserIdAndCategory(userId, category));
        assertEquals("DB failure", thrown.getMessage());
    }
}