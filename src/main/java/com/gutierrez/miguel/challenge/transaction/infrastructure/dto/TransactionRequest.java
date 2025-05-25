package com.gutierrez.miguel.challenge.transaction.infrastructure.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(UUID cardholderId, BigDecimal amount, String description) {}
