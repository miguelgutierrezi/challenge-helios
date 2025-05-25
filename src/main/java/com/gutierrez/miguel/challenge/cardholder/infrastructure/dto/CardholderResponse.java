package com.gutierrez.miguel.challenge.cardholder.infrastructure.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CardholderResponse(UUID id, String name, String email, BigDecimal balance) {}
