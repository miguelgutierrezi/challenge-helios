package com.gutierrez.miguel.challenge.cardholder.domain.model.vo;

import java.math.BigDecimal;

public record Balance(BigDecimal value) {
    public Balance {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance must be non-null and non-negative");
        }
    }
}
