package com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.mapper;

import com.gutierrez.miguel.challenge.cardholder.domain.model.Cardholder;
import com.gutierrez.miguel.challenge.cardholder.infrastructure.repository.entity.CardholderEntity;
import org.springframework.stereotype.Component;

@Component
public class CardholderMapper {

    public CardholderEntity toEntity(Cardholder cardholder) {
        return CardholderEntity.builder()
                .id(cardholder.getId())
                .name(cardholder.getName())
                .email(cardholder.getEmail())
                .build();
    }

    public Cardholder toDomain(CardholderEntity cardholderEntity) {
        return Cardholder.builder()
                .id(cardholderEntity.getId())
                .name(cardholderEntity.getName())
                .email(cardholderEntity.getEmail())
                .build();
    }

}
