package com.gutierrez.miguel.challenge.transaction.infrastructure.repository.entity;

import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.entity.CardholderEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {

    @Id
    private UUID id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "cardholder_id", nullable = false)
    private CardholderEntity cardholder;
}
