package com.gutierrez.miguel.challenge.transaction.infrastructure.controller;

import com.gutierrez.miguel.challenge.transaction.application.usecases.CreateTransactionService;
import com.gutierrez.miguel.challenge.transaction.domain.model.Transaction;
import com.gutierrez.miguel.challenge.transaction.infrastructure.dto.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final CreateTransactionService createTransactionService;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody TransactionRequest request) {
        Transaction tx = createTransactionService.execute(request.cardholderId(), request.amount(), request.description());
        return ResponseEntity.ok(tx);
    }
}
