package com.gutierrez.miguel.challenge.notification.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gutierrez.miguel.challenge.notification.application.usecases.UpdateNotificationPreferenceService;
import com.gutierrez.miguel.challenge.notification.domain.model.vo.NotificationCategory;
import com.gutierrez.miguel.challenge.notification.infrastructure.controller.dto.UpdateNotificationPreferenceRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationPreferenceController.class)
class NotificationPreferenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UpdateNotificationPreferenceService updateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUpdateNotificationPreferenceSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        UpdateNotificationPreferenceRequest request = new UpdateNotificationPreferenceRequest(
                NotificationCategory.GAME_EVENT,
                true
        );

        mockMvc.perform(put("/notification-preferences/update/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        Mockito.verify(updateService).updatePreference(userId, NotificationCategory.GAME_EVENT, true);
    }
}