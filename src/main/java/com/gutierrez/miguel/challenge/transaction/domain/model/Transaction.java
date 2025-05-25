package com.gutierrez.miguel.challenge.transaction.domain.model;

import com.gutierrez.miguel.challenge.transaction.domain.model.vo.TransactionAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private UUID id;
    private UUID cardholderId;
    private TransactionAmount amount;
    private String description;
    private LocalDateTime date;
}
