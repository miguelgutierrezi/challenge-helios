package com.gutierrez.miguel.challenge.notification.domain.model.vo;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum NotificationType {

    // Game Events
    LEVEL_UP(NotificationCategory.GAME_EVENT),
    ITEM_ACQUIRED(NotificationCategory.GAME_EVENT),
    CHALLENGE_COMPLETED(NotificationCategory.GAME_EVENT),
    PVP_ATTACKED(NotificationCategory.GAME_EVENT),

    // Social Events
    FRIEND_REQUEST(NotificationCategory.SOCIAL_EVENT),
    FRIEND_ACCEPTED(NotificationCategory.SOCIAL_EVENT),
    NEW_FOLLOWER(NotificationCategory.SOCIAL_EVENT);

    private final NotificationCategory category;

    NotificationType(NotificationCategory category) {
        this.category = category;
    }

    public static List<NotificationType> getByCategory(NotificationCategory category) {
        return Arrays.stream(NotificationType.values())
                .filter(type -> type.getCategory() == category)
                .toList();
    }

}
