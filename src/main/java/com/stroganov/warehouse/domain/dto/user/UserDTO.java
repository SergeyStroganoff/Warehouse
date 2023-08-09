package com.stroganov.warehouse.domain.dto.user;

import com.stroganov.warehouse.domain.model.user.Authorities;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;

import java.util.List;
import java.util.Set;

public record UserDTO(
        String userName,
        String password,
        String fullName,
        String email,
        boolean enabled,
        Set<Authorities> authorities,
        List<Warehouse> warehouseList
) {
}
