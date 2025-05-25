package com.gutierrez.miguel.challenge.cardholder.domain.model;

import com.gutierrez.miguel.challenge.cardholder.domain.exception.InsufficientBalanceException;
import com.gutierrez.miguel.challenge.cardholder.domain.model.vo.Balance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cardholder {
    private UUID id;
    private String name;
    private String email;
    private Balance balance;

    public void increaseBalance(BigDecimal amount) {
        this.balance = new Balance(this.balance.value().subtract(amount));
    }

    public void decreaseBalance(BigDecimal amount) {
        if (this.balance.value().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Cardholder has insufficient balance");
        }
        this.balance = new Balance(this.balance.value().subtract(amount));
    }
}
