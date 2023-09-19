package com.stroganov.warehouse.domain.dto.transaction;

import lombok.Getter;

@Getter
public enum DeliveryParserOptions {
    BASIC_TEMPLATE("Basic Template"),
    NORDIC_TEMPLATE("Nordic Color Kitchen");
    private final String parserName;

    DeliveryParserOptions(String parserName) {
        this.parserName = parserName;
    }
}
