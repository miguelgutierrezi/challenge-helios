package com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.adapter;

import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.entity.CardholderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaCardholderRepository extends JpaRepository<CardholderEntity, UUID> {}
