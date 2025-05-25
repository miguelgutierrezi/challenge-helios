package com.gutierrez.miguel.challenge.cardholder.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cardholder {
    private UUID id;
    private String name;
    private String email;
}
