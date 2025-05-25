package com.gutierrez.miguel.challenge.shared.exceptions;

import com.gutierrez.miguel.challenge.user.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestController
@RequestMapping("/test")
class ExceptionTestController {

    @GetMapping("/not-found")
    public void throwNotFound() {
        throw new UserNotFoundException("User not found");
    }

    @GetMapping("/bad-request")
    public void throwBadRequest() {
        throw new IllegalArgumentException("Invalid argument");
    }

    @GetMapping("/generic")
    public void throwGeneric() {
        throw new RuntimeException("Unexpected failure");
    }
}

@WebMvcTest(controllers = ExceptionTestController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnNotFoundForUserNotFoundException() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    void shouldReturnBadRequestForIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/test/bad-request"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid argument"));
    }

    @Test
    void shouldReturnInternalServerErrorForGenericException() throws Exception {
        mockMvc.perform(get("/test/generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred: Unexpected failure"));
    }
}