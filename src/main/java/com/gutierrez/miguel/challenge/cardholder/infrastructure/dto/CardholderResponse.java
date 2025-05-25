package com.gutierrez.miguel.challenge.cardholder.infrastructure.dto;

import java.util.UUID;

public record CardholderResponse(UUID id, String name, String email) {}
