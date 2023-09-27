package com.stroganov.warehouse.domain.dto.transaction;

import lombok.Getter;

@Getter
public enum FileTypeOptions {
    BASIC_TEMPLATE("Basic Template"),
    NORDIC_TEMPLATE("Nordic Color Kitchen");
    private final String parserName;

    FileTypeOptions(String parserName) {
        this.parserName = parserName;
    }
}
