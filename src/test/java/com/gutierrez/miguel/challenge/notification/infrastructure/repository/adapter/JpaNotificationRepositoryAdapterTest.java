package com.gutierrez.miguel.challenge.notification.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.entity.NotificationEntity;
import com.gutierrez.miguel.challenge.notification.infrastructure.repository.mapper.NotificationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaNotificationRepositoryAdapterTest {

    @Mock
    private JpaNotificationRepository jpaRepository;

    @Mock
    private NotificationMapper mapper;

    @InjectMocks
    private JpaNotificationRepositoryAdapter adapter;

    private Notification domainNotification;
    private NotificationEntity entityNotification;

    @BeforeEach
    public void setUp() {
        UUID id = UUID.randomUUID();
        domainNotification = Notification.builder()
                .id(id)
                .build();

        entityNotification = NotificationEntity.builder()
                .id(id)
                .build();
    }

    @Test
    public void shouldSaveNotificationSuccessfully() {
        when(mapper.toEntity(domainNotification)).thenReturn(entityNotification);
        when(jpaRepository.save(entityNotification)).thenReturn(entityNotification);
        when(mapper.toDomain(entityNotification)).thenReturn(domainNotification);

        Notification result = adapter.save(domainNotification);

        assertEquals(domainNotification, result);
        verify(jpaRepository).save(entityNotification);
    }

    @Test
    public void shouldThrowExceptionWhenSavingNotificationFails() {
        when(mapper.toEntity(domainNotification)).thenReturn(entityNotification);
        when(jpaRepository.save(entityNotification)).thenThrow(new RuntimeException("DB Failure"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adapter.save(domainNotification);
        });

        assertEquals("DB Failure", exception.getMessage());
        verify(jpaRepository).save(entityNotification);
    }
}