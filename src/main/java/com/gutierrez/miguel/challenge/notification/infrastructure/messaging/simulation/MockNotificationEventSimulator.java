package com.gutierrez.miguel.challenge.notification.infrastructure.messaging.simulation;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.domain.ports.CardholderRepositoryPort;
import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@Profile("mock")
@RequiredArgsConstructor
public class MockNotificationEventSimulator {

    private final SendNotificationService sendNotificationService;
    private final CardholderRepositoryPort cardholderRepository;
    private final Random random = new Random();

    private static final Map<NotificationCategory, List<NotificationType>> NOTIFICATION_CATEGORIES = Map.of(
            NotificationCategory.GAME_EVENT, List.of(
                    NotificationType.LEVEL_UP,
                    NotificationType.CHALLENGE_COMPLETED,
                    NotificationType.ITEM_ACQUIRED,
                    NotificationType.PVP_ATTACKED
            ),
            NotificationCategory.SOCIAL_EVENT, List.of(
                    NotificationType.FRIEND_REQUEST,
                    NotificationType.FRIEND_ACCEPTED,
                    NotificationType.NEW_FOLLOWER
            )
    );

    private static final List<String> SAMPLE_MESSAGES = List.of(
            "You’ve been invited to a new game!",
            "Your friend just sent you a gift!",
            "You’ve unlocked a new achievement!",
            "Your score just got beat!",
            "New social event: Clash of Clans Tournament!",
            "Guild chat: New message from teammate!"
    );

    @Scheduled(fixedRate = 10000)
    public void simulateNotificationEvent() {
        List<NotificationCategory> categories = new ArrayList<>(NOTIFICATION_CATEGORIES.keySet());
        NotificationCategory category = categories.get(random.nextInt(categories.size()));

        List<NotificationType> types = NOTIFICATION_CATEGORIES.get(category);
        NotificationType type = types.get(random.nextInt(types.size()));

        String content = SAMPLE_MESSAGES.get(random.nextInt(SAMPLE_MESSAGES.size()));

        List<UUID> cardholders = cardholderRepository.findAll().stream().map(Cardholder::getId).toList();
        if (cardholders.isEmpty()) {
            log.warn("No cardholders found. Skipping simulation.");
            return;
        }

        UUID randomUUID = cardholders.get(random.nextInt(cardholders.size()));

        Notification notification = sendNotificationService.execute(
                randomUUID,
                type,
                content
        );

        log.info("Mock notification sent: {}", notification);
    }
}
