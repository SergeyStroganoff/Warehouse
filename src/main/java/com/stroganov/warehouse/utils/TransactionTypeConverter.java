package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.transaction.TransactionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {
    @Override
    public String convertToDatabaseColumn(TransactionType transactionType) {
        if (transactionType == null) {
            return null;
        }
        return transactionType.getCode();
    }

    @Override
    public TransactionType convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return Stream.of(TransactionType.values())
                .filter(t -> t.getCode().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
