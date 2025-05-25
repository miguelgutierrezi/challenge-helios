package com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "cardholders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardholderEntity {
    @Id
    private UUID id;
    private String name;
    private String email;
}
