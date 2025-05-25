package com.gutierrez.miguel.challenge.user.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gutierrez.miguel.challenge.user.application.usecases.CreateUserService;
import com.gutierrez.miguel.challenge.user.application.usecases.GetAllUsersService;
import com.gutierrez.miguel.challenge.user.domain.model.User;
import com.gutierrez.miguel.challenge.user.infrastructure.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserService createUserService;

    @MockBean
    private GetAllUsersService getAllUsersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        String name = "John Doe";
        String email = "john@example.com";
        User user = new User(userId, name, email);

        UserRequest request = new UserRequest(name, email);

        Mockito.when(createUserService.create(name, email)).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(userId.toString())))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    void shouldGetAllUsersSuccessfully() throws Exception {
        User user1 = new User(UUID.randomUUID(), "Alice", "alice@example.com");
        User user2 = new User(UUID.randomUUID(), "Bob", "bob@example.com");

        Mockito.when(getAllUsersService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(user1.getId().toString())))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[0].email", is("alice@example.com")))
                .andExpect(jsonPath("$[1].id", is(user2.getId().toString())))
                .andExpect(jsonPath("$[1].name", is("Bob")))
                .andExpect(jsonPath("$[1].email", is("bob@example.com")));
    }
}