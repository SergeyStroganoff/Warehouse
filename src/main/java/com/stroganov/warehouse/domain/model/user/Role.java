package com.stroganov.warehouse.domain.model.user;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_WAREHOUSE("WAREHOUSE"),
    ROLE_ACCOUNTANT("ACCOUNTANT");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
