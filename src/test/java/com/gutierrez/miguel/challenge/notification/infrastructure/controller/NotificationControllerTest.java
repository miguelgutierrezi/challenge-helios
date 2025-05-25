package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gutierrez.miguel.challenge.notification.application.usecases.SendNotificationService;
import com.gutierrez.miguel.challenge.notification.domain.model.Notification;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationContent;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationRecipient;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationTimestamp;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationType;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.SendNotificationRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SendNotificationService sendNotificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSendNotificationSuccessfully() throws Exception {
        UUID recipientId = UUID.randomUUID();
        NotificationType type = NotificationType.LEVEL_UP;
        String message = "Congrats!";

        SendNotificationRequest request = new SendNotificationRequest(recipientId, type, message);

        Notification expectedNotification = new Notification(
                UUID.randomUUID(),
                new NotificationRecipient(recipientId),
                new NotificationContent(message),
                type,
                new NotificationTimestamp(LocalDateTime.now()),
                NotificationCategory.GAME_EVENT
        );

        Mockito.when(sendNotificationService.execute(recipientId, type, message))
                .thenReturn(expectedNotification);

        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipient", is(recipientId.toString())))
                .andExpect(jsonPath("$.content", is(message)))
                .andExpect(jsonPath("$.type", is(type.name())))
                .andExpect(jsonPath("$.category", is(expectedNotification.getCategory().name())));
    }
}