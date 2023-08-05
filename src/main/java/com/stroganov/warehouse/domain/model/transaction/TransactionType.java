package com.stroganov.warehouse.domain.model.transaction;

import lombok.Getter;

@Getter
public enum TransactionType {

    SUPPLY("supply", "sup"),
    CONSUMPTION("consumption", "con"),
    DELETE("delete", "del"),
    CORRECTION("correction", "cor");
    private final String typeName;
    private final String code;

    TransactionType(String typeName, String code) {
        this.typeName = typeName;
        this.code = code;
    }
}

